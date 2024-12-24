package jca.springframework.builder.exception;

import java.util.ArrayList;
import java.util.List;

import jca.springframework.exception.FrameworkException;
import jca.springframework.view.View;

public class FieldsValidationException extends FrameworkException {
    private final List<FieldValidationException> fieldExceptions;
    private final List<FieldValidationException> validList;

    public FieldsValidationException(List<FieldValidationException> fieldExceptions, List<FieldValidationException> validList) {
        super(null, null);
        this.fieldExceptions = fieldExceptions;
        this.validList = validList;
    }
    public FieldsValidationException() {
        super(null, null);
        this.fieldExceptions = new ArrayList<>();
        this.validList = new ArrayList<>();
    }

    public List<FieldValidationException> getFieldExceptions() {
        return fieldExceptions;
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder("Validation errors:\n");
        for (FieldValidationException exception : fieldExceptions) {
            message.append("Field ").append(exception.getField().getName()).append(": ")
                    .append(exception.getMessage()).append("\n");
        }
        return message.toString();
    }

    public void setErrorAttributes(View view){
        for (FieldValidationException fieldValidationException : fieldExceptions) {
            fieldValidationException.setErrorAttribut(view);
        }
    }
    public void setValidAttributes(View view){
        for (FieldValidationException fieldValidationException : validList) {
            fieldValidationException.setValidAttribute(view);
        }
    }

    public void setValidationAttributes(View view){
        setErrorAttributes(view);
        setValidAttributes(view);
    }
    public List<FieldValidationException> getValidList() {
        return validList;
    }
}
