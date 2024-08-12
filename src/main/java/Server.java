import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int SERVER_SOCKET;
    private final List<String> validPaths = List.of("/index.html", "/spring.svg",
            "/spring.png", "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html",
            "/events.html", "/events.js");
    private final ExecutorService executorService;
    private final ConcurrentHashMap<String, Map<String, Handler>> handlers;
    public Server(int serverSocket, int poolSize) {
        SERVER_SOCKET = serverSocket;
        this.executorService = Executors.newFixedThreadPool(poolSize);
        handlers = new ConcurrentHashMap<>();
    }
    void start() {
        try (final var serverSocket = new ServerSocket(SERVER_SOCKET)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> proceedConnection(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public void proceedConnection(Socket socket) {

        try (final var in = new BufferedInputStream(socket.getInputStream());
             final var out = new BufferedOutputStream(socket.getOutputStream())) {

            Request request = Request.createRequest(in);
            if (request == null || !handlers.containsKey(request.getRequestMethod())) {
                responseWithoutContent(out, "400", "Bad Request");
                return;
            } else {
                System.out.println("Request debug information: ");
                System.out.println("METHOD: " + request.getRequestMethod());
                System.out.println("PATH: " + request.getRequestPath());
                System.out.println("HEADERS: " + request.getHeaders());
                System.out.println("Query Params: ");
                for (var para : request.getQueryParams()) {
                    System.out.println(para.getName() + " = " + para.getValue());
                }

                System.out.println("Test for dumb param name: ");
                System.out.println(request.getQueryParam("YetAnotherDumb").getName());
                System.out.println("Test for dumb  param  name-value: ");
                System.out.println(request.getQueryParam("testDebugInfo").getValue());
            }

            Map<String, Handler> handlerMap = handlers.get(request.getRequestMethod());
            String requestPath = request.getRequestPath();
            if (handlerMap.containsKey(requestPath)) {
                Handler handler = handlerMap.get(requestPath);
                handler.handle(request, out);
            } else {
                if (!validPaths.contains(request.getRequestPath())) {
                    responseWithoutContent(out, "404", "Not Found");
                } else {
                    defaultHandler(out, request.getRequestPath());
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    void responseWithoutContent(BufferedOutputStream out, String responseCode, String responseStatus)
            throws IOException {
        out.write((
                "HTTP/1.1 " + responseCode + " " + responseStatus + "\r\n" +
                        "Content-Length: 0\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes());
        out.flush();
    }
void defaultHandler(BufferedOutputStream out, String path) throws IOException {
    final var filePath = Path.of(".", "public", path);
    final var mimeType = Files.probeContentType(filePath);

    if (path.equals("/classic.html")) {
        final var template = Files.readString(filePath);
        final var content = template.replace(
                "{time}",
                LocalDateTime.now().toString()
        ).getBytes();
        out.write((
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + mimeType + "\r\n" +
                        "Content-Length: " + content.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes());
        out.write(content);
        out.flush();
        return;
    }
    final var length = Files.size(filePath);
    out.write((
            "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: " + mimeType + "\r\n" +
                    "Content-Length: " + length + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n"
    ).getBytes());
    Files.copy(filePath, out);
    out.flush();
    }
    void addHandler(String method, String path, Handler handler) {
        if (!handlers.containsKey(method)) {
            handlers.put(method, new HashMap<>());
        }
        handlers.get(method).put(path, handler);
    }
}
