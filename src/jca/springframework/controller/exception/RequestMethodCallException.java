package jca.springframework.controller.exception;

import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.Mapping;

public class RequestMethodCallException extends FrameworkException {

    public RequestMethodCallException( Mapping mapping , String requestMethod ) {
        super(createMessage(mapping, requestMethod), null);
    }

    private static String createMessage(Mapping mapping , String requstMethod){
        String url = mapping.getMappingAnnotation().getUrl();
        String verb = mapping.getMappingAnnotation().getVerb();

        return "La methode "+requstMethod+" utilisee pour l'appel de l'url : "+url+"; Excepted methode "+verb+"";
    }

}
