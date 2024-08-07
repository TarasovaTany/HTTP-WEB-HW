public class Request {
    private final String requestMethod;
    private final String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }
    public String getRequestMethod() {
        return requestMethod;
    }
    public String getRequestPath() {
        return requestPath;
    }
}
