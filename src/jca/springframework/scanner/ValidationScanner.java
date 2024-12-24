package jca.springframework.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jca.springframework.annotations.attribut.validation.Max;
import jca.springframework.annotations.attribut.validation.Min;
import jca.springframework.annotations.attribut.validation.Required;
import jca.springframework.builder.exception.FieldValidationException;
import jca.springframework.builder.exception.FieldsValidationException;
import jca.springframework.utils.AnnotationUtils;

public class ValidationScanner {
    private List<FieldValidationException> exceptionList;
    private FieldsValidationException validationException;

    public ValidationScanner(){
        setFieldValidationExceptions(new ArrayList<FieldValidationException>());
    }
    
    // Récupérer les annotations pour un attribut donné
    public List<Annotation> getValidationField(Field field) {
        List<Annotation> annotations = new ArrayList<>();
        for (Annotation annotation : field.getAnnotations()) {
            if (
                    annotation instanceof Required 
                ||  annotation instanceof Min
                ||  annotation instanceof Max    
            ) {
                annotations.add(annotation);
            }
        }
        return annotations;
    }
    
    // Vérifier les annotations d'un attribut et ajouter les exceptions dans une liste si nécessaire
    public void checkValidationField(Field field, Object object) {
        field.setAccessible(true);
        try {
            Object value = field.get(object);
            checkValidationFieldValue(field, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        finally {
            field.setAccessible(false);
        }
    }
    // Vérifier les annotations d'un attribut et ajouter les exceptions dans une liste si nécessaire
    public void checkValidationFieldValue(Field field, Object value) {
        for (Annotation annotation : getValidationField(field)) {
            if (annotation instanceof Required && value == null) {
                addFieldException(field, annotation, value);
            } else if (
                annotation instanceof Min && 
                value instanceof Integer && 
                (Integer) value < ((Min) annotation).value()
            ) {
                addFieldException(field, annotation, value);
            } else if (
                annotation instanceof Max && 
                value instanceof Integer && 
                (Integer) value > ((Max) annotation).value()
            ) {
                addFieldException(field, annotation, value);
            }
        }
    }

    protected void addFieldException(Field field , Annotation annotation , Object value){
        String message = AnnotationUtils.getValidationMessage(annotation, value);
        getFieldValidationExceptions().add(new FieldValidationException(field, annotation,message));
    }
    
    // Vérifier toutes les annotations d'un tableau de champs
    public void checkValidationFields(Field[] fields, Object object) {
        for (Field field : fields) {
            checkValidationField(field, object);
        }
    }

    protected List<FieldValidationException> getFieldValidationExceptions() {
        return exceptionList;
    }
    
    protected void setFieldValidationExceptions(List<FieldValidationException> exceptionList) {
        this.exceptionList = exceptionList;
    }

    public FieldsValidationException getValidationExceptions(){
        if (this.validationException  == null) {
            this.validationException = new FieldsValidationException(getFieldValidationExceptions()); 
        }
        return this.validationException;
    }

    public void thowExceptionIfNeeded() throws FieldsValidationException{
        if (this.getFieldValidationExceptions().size() > 0) {
            throw this.getValidationExceptions();
        }
    }
}
