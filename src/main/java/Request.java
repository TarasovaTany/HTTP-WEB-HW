public class Request {
    private final String requestBody;
    private final String requestMethod;
    private final String requestPath;
    private final String requestProtocol;
    private final String messageBody;
    public Request(String requestBody, String requestMethod, String requestPath, String requestProtocol, String messageBody) {
        this.requestBody = requestBody;
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.requestProtocol = requestProtocol;
        this.messageBody = messageBody;
    }

    public String getRequestBody() {
        return requestBody;
    }
    public String getRequestMethod() {
        return requestMethod;
    }
    public String getRequestPath() {
        return requestPath;
    }
    public String getRequestProtocol() {
        return requestProtocol;
    }
    public String getMessageBody() {
        return requestBody;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Request)) return false;

         Request request = (Request) object;

        if (!getRequestBody().equals(request.getRequestBody())) return false;
        return getMessageBody() != null ? getMessageBody().equals(request.getRequestBody()) :
                request.getMessageBody() == null;
    }
    @Override
    public int hashCode() {
        int result = getRequestBody().hashCode();
        result = 31 * result + (getMessageBody() != null ? getMessageBody().hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        return "Request {" +
                "requestBody='" + requestBody + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestPath='" + requestPath + '\'' +
                ", requestProtocol='" + requestProtocol + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }
}
