package jca.springframework.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import jca.springframework.annotations.attribut.validation.Max;
import jca.springframework.annotations.attribut.validation.Min;
import jca.springframework.annotations.attribut.validation.Required;

public class AnnotationUtils {

    public static String getValidationMessage(Annotation annotation,Object value){
        String message = null;
        if (annotation instanceof Required && value == null) {
            message = ((Required) annotation).message();
        } else if (annotation instanceof Min && value instanceof Integer && (Integer) value < ((Min) annotation).value()) {
            message = ((Min) annotation).message().replace("{value}", String.valueOf(((Min) annotation).value()));
        } else if (annotation instanceof Max && value instanceof Integer && (Integer) value > ((Max) annotation).value()) {
            message = ((Max) annotation).message().replace("{value}", String.valueOf(((Max) annotation).value()));
        }
        return message;
    }
    public static String getValidationMessage(Field field,Object object,Annotation annotation) throws IllegalArgumentException, IllegalAccessException{
        String message = getValidationMessage(annotation, field.get(object));
        return message;
    }
    
}
