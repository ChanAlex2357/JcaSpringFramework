package jca.springframework.builder.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import jca.springframework.exception.FrameworkException;
import jca.springframework.view.View;


public class FieldValidationException extends FrameworkException {
    private  Field field;
    private  Annotation annotation;
    private  Object value;   

    public FieldValidationException(Field field, Annotation annotation , Object value, String message) {
        super(message,null);
        setField(field);
        setAnnotation(annotation);
        setValue(value);
    }

    public Field getField() {
        return field;
    }

    public Annotation getAnnotations() {
        return annotation;
    }

    protected String getErrorName(){
        return "error_"+getField().getName();
    }
    protected String getValueName(){
        return "value_"+getField().getName();
    }
    public void setErrorAttribut(View view) {
        if (this.getValue() == null) {
            return;
        }
        // Ajouter le message d'erreur parmi les attributs de la requete
        if (this.getMessage() != null) {
            view.addObject(this.getErrorName(),this.getMessage());
        }
        view.addObject(this.getValueName(),this.getValue());
    }
    public void setValidAttribute(View view) {
        if (this.getValue() == null) {
            return;
        }
        view.addObject(this.getValueName(),this.getValue());
    }

    private void setField(Field field) {
        this.field = field;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    private void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Object getValue() {
        return value;
    }

    private void setValue(Object value) {
        this.value = value;
    }
    
}
