package jca.springframework.controller.exception;

public class UnhandledUrlException extends Exception {
    public UnhandledUrlException(String url){
        super( urlMappingErrorMessage(url));
    }
    static public String urlMappingErrorMessage(String url){
        return "\t L'url que vous demander n'est gerer par aucun Controller \t URL :"+url;
    }
}
