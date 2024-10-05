package jca.springframework.mapping;
import java.lang.reflect.Method;
import jca.springframework.scanner.MethodScanner;

public class MappingAnnotation {
    private String url;
    private String verb;
    private boolean isApiMethode;

    @Override
    public String toString() {
        return "[ "+url+" ; "+verb+" ; "+isApiMethode+" ]";
    }
    public boolean isApiMethode() {
        return isApiMethode;
    }
    public void setApiMethode(Method method) {
        setApiMethode(MethodScanner.isRestApiMethode(method));
    }
    public void setApiMethode(boolean api) {
        this.isApiMethode = api;
    }
    
    public MappingAnnotation ( Method method ){
        setUrl(MethodScanner.getMethodeUrl(method));
        setVerb(MethodScanner.getMethodeVerb(method));
        setApiMethode(method);
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }   
    public String getVerb() {
        return verb;
    }
    public void setVerb(String verb) {
        this.verb = verb;
    }
}
