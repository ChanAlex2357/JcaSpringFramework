package jca.springframework.scanner;

import java.lang.annotation.Annotation;

import jca.springframework.annotations.attribut.validation.Max;
import jca.springframework.annotations.attribut.validation.Min;
import jca.springframework.annotations.attribut.validation.Required;
import jca.springframework.utils.StringUtils;

public class AnnotationChecker {
    public static String check(Object value , Annotation annotation){
        String message = null;
        if (annotation instanceof Required) {
            System.out.println("AnnotationChecker.check() : Required");
            message = checkRequired(value, annotation);
        } else if ( annotation instanceof Min) {
            message = checkMin(value, annotation);
        } else if ( annotation instanceof Max) {
            message = checkMax(value, annotation);
        }
        return message;
    }
    protected static String checkRequired(Object value , Annotation annotation){
        if (value == null || value == "" || value == "null") {
            return ((Required) annotation).message();
        }
        return null;
    }

    protected Object getRequiredForced(Object value) {
        if ((value instanceof String && value.equals("")) || (value instanceof Double && (Double) value == 0)) {
            return "";
        }
        return value;
    }

    protected static String checkMin(Object value , Annotation annotation) {
        boolean forced = false;
        if ( value instanceof String && value.equals("")) {
            value = "0"; 
            forced = true;
        }
        if (value instanceof Number) {
            double numericValue = ((Number) value).doubleValue();
            if (numericValue < ((Min) annotation).value()) {
            if (forced) {
                value = "";
            }
            return StringUtils.replacement(
                ((Min) annotation).message(),
                "{value}",
                String.valueOf(((Min) annotation).value())
            );
            }
        }

        return null;
    }

    protected static String checkMax(Object value , Annotation annotation){
        boolean forced = false;
        if ( value instanceof String && value.equals("")) {
            value = "0"; 
            forced = true;
        }
        if (value instanceof Number) {
            double numericValue = ((Number) value).doubleValue();
            if (numericValue > ((Max) annotation).value()) {
            if (forced) {
                value = "";
            }
            return StringUtils.replacement(
                ((Max) annotation).message(),
                "{value}",
                String.valueOf(((Max) annotation).value())
            );
            }
        }
        return null;
    }
    
}
