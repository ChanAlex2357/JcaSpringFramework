package jca.springframework.controller.exception;

import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.Mapping;

public class DuplicateUrlException extends FrameworkException {

    
    String url;
    Mapping old;
    Mapping newer;
    public DuplicateUrlException(String url,Mapping old , Mapping newer){
        setUrl(url);
        setOld(old);
        setNewer(newer);
    }
    @Override
    public String getMessage() {
        return " L'url <"+url+"> est gerer par plusieur controlleur :\n\t - "+old+"\n\t - "+newer;
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Mapping getOld() {
        return old;
    }
    public void setOld(Mapping old) {
        this.old = old;
    }
    public Mapping getNewer() {
        return newer;
    }
    public void setNewer(Mapping newer) {
        this.newer = newer;
    }
    
    
}