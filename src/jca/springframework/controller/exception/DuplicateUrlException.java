package jca.springframework.controller.exception;

import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.Mapping;

public class DuplicateUrlException extends FrameworkException{
    public DuplicateUrlException(String url,Mapping old , Mapping newer){
        super(errorMessage(url, old, newer),null);
    }
    static private String errorMessage(String url,Mapping old , Mapping newer){
        return " L'url <"+url+"> est gerer par plusieur controlleur :\n\t - "+old+"\n\t - "+newer;
    }
}
