import java.io.IOException;

public class Main {
    private static final int SERVER_PORT = 9999;
    private static final int THREAD_POOL_SIZE = 64;

    public static void main(String[] args) {
        Server server = new Server(SERVER_PORT, THREAD_POOL_SIZE);

        // добавление хендлеров (обработчиков)
        server.addHandler("GET", "/messages", (request, responseStream) -> {
            server.responseWithoutContent(responseStream, "404", "Not found");
        });

        server.addHandler("POST", "/messages", (request, responseStream) ->
                server.responseWithoutContent(responseStream, "503", "Service Unavailable"));

        server.addHandler("GET", "/messages", (request, outputStream) -> {
                try {
                server.defaultHandler(outputStream, "index.html");
                    } catch (IOException e) {
                e.printStackTrace();

        }
    });
        server.start();
    }
}

