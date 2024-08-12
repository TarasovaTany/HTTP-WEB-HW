import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Request {
    private final String requestMethod;
    private final String requestPath;
    private final List<String> headers;
    private List<NameValuePair> parameters;
    private static final int LIMIT_REQUEST = 4096;
    public static final String GET = "GET";
    public static final String POST = "POST";
    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        headers = null;
    }
    public Request(String requestMethod, String requestPath, List<String> headers, List<NameValuePair> parameters) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.headers = headers;
        this.parameters = parameters;
    }
    public String getRequestMethod() {
        return requestMethod;
    }
    public String getRequestPath() {
        return requestPath;
    }
    public List<NameValuePair> getParameters() {
        return parameters;
    }
    public List<String> getHeaders() {
        return headers;
    }
    public List<NameValuePair> getQueryParams() {
        return parameters;
    }
    public NameValuePair getQueryParam(String name) {
        return getQueryParams().stream()
                .filter(param -> param.getName().equalsIgnoreCase(name))
                .findFirst().orElse(new NameValuePair() {
                    @Override
                    public String getName() {
                        return null;
                    }

                    @Override
                    public String getValue() {
                        return null;
                    }
                });
    }
static Request createRequest(BufferedInputStream in) throws IOException, URISyntaxException {
    final List<String> allowedMethods = List.of(GET, POST);

    final var limit = LIMIT_REQUEST;
    in.mark(limit);
    final var buffer = new byte[limit];
    final var read = in.read(buffer);

    final var requestLineDelimiter = new byte[]{'\r', '\n'};
    final var requestLineEnd = indexOf(buffer, requestLineDelimiter, 0, read);
    if (requestLineEnd == -1) {
        return null;
    }

    final var requestLine = new String(Arrays.copyOf(buffer, requestLineEnd)).split(" ");
    if (requestLine.length != 3) {
        return null;
    }

    final var method = requestLine[0];
    if (!allowedMethods.contains(method)) {
        return null;
    }

    final var path = requestLine[1].split("\\?")[0];

    final var headersDelimiter = new byte[]{'\r', '\n', '\r', '\n'};
    final var headersStart = requestLineEnd + requestLineDelimiter.length;
    final var headersEnd = indexOf(buffer, headersDelimiter, headersStart, read);
    if (headersEnd == -1) {
        return null;
    }

    in.reset();
    in.skip(headersStart);

    final var headersBytes = in.readNBytes(headersEnd - headersStart);
    List<String> headers = Arrays.asList(new String(headersBytes).split("\r\n"));

    List<NameValuePair> parameters = URLEncodedUtils.parse(new URI(requestLine[1]), StandardCharsets.UTF_8);
    return new Request(method, path, headers, parameters);
}
private static int indexOf(byte[] array, byte[] target, int start, int max) {
    outer:
    for (int i = start; i < max - target.length + 1; i++) {
        for (int j = 0; j < target.length; j++) {
            if (array[i + j] != target[j]) {
                continue outer;
            }
        }
        return i;
    }
    return -1;
    }
}
