package jca.springframework.annotations;

import java.lang.annotation.Annotation;

public class MappingAnnotation {
    private Annotation annotation;
    private String url;
    public MappingAnnotation(Annotation annotation , String url){
        setAnnotation(annotation);
        setUrl(url);
    }
    public Annotation getAnnotation() {
        return annotation;
    }
    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }   
}
