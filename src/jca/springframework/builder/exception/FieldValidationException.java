package jca.springframework.builder.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import jakarta.servlet.http.HttpServletRequest;


public class FieldValidationException extends Exception {
    private final Field field;
    private final Annotation[] annotations;
    private final String message;

    public FieldValidationException(Field field, Annotation annotation, String message) {
        super(message);
        this.field = field;
        this.annotations = new Annotation[]{annotation};
        this.message = message;
    }

    public Field getField() {
        return field;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public String getMessage() {
        return message;
    }
    protected String getErrorName(){
        return "error"+getField().getName().toUpperCase();
    }
    public void setErrorAttribut(HttpServletRequest request) {
        request.setAttribute(this.getErrorName(),this.getMessage());
    }
}
