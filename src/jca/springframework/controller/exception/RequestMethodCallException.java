package jca.springframework.controller.exception;

import jca.springframework.exception.FrameworkException;

public class RequestMethodCallException extends FrameworkException {
    String url;
    String requestMethod;
    public RequestMethodCallException(  String url, String requestMethod ) {
        setUrl(url);
        setRequestMethod(requestMethod);
    }
    @Override
    public String getMessage() {
        return "La methode "+requestMethod+" utilisee pour l'appel de l'url : "+url+" n'est pas possible";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
