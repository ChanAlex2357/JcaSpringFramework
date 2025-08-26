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

public class ValidationScanner {
    private FieldsValidationException fieldsValidationException;

    public ValidationScanner(){
        setValidationException(new FieldsValidationException());
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
        String message = null;
        // Get error message for each annotation field for validation
        for (Annotation annotation : getValidationField(field)) {
            message = new AnnotationChecker().check(value, annotation);
            if (message != null) {
                addFieldException(field, annotation, value , message);
            }
            else {
                addValidField(field, annotation, value);
            }
        }
    }
    protected void addFieldException(Field field , Annotation annotation , Object value , String message){
        getValidationException().getFieldExceptions().add(new FieldValidationException(field, annotation,value,message));
    }
    protected void addValidField(Field field , Annotation annotation , Object value){
        getValidationException().getValidList().add(new FieldValidationException(field, annotation,value,"valide"));
    }
    
    // Vérifier toutes les annotations d'un tableau de champs
    public void checkValidationFields(Field[] fields, Object object) {
        for (Field field : fields) {
            checkValidationField(field, object);
        }
    }
    
    public void thowExceptionIfNeeded() throws FieldsValidationException{
        if (isValidationErrorPresent()) {
            throw this.getValidationException();
        }
    }

    public boolean isValidationErrorPresent(){
        return !this.getValidationException().getFieldExceptions().isEmpty() || this.getValidationException().getFieldExceptions().size() > 0;
    }

    public FieldsValidationException getValidationException() {
        return this.fieldsValidationException;
    }

    public void setValidationException(FieldsValidationException fieldsValidationException) {
        this.fieldsValidationException = fieldsValidationException;
    }
}
