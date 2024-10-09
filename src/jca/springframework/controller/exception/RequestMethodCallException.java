package jca.springframework.controller.exception;

import jca.springframework.exception.FrameworkException;

public class RequestMethodCallException extends FrameworkException {

    public RequestMethodCallException(  String url, String requestMethod ) {
        super(createMessage(url,requestMethod), null);
    }

    private static String createMessage( String url, String requestMethod){

        return "La methode "+requestMethod+" utilisee pour l'appel de l'url : "+url+" n'est pas possible";
    }

}
