package jca.springframework.controller.exception;

import jca.springframework.exception.FrameworkException;

public class UnhandledUrlException extends FrameworkException {
    public UnhandledUrlException(String url){
        super( urlMappingErrorMessage(url)  , null);
    }
    static public String urlMappingErrorMessage(String url){
        return "\tL'url que vous demander n'est gerer par aucun Controller \n\tURL :"+url;
    }
}
