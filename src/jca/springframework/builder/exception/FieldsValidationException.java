package jca.springframework.builder.exception;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.exception.FrameworkException;

public class FieldsValidationException extends FrameworkException {
    private final List<FieldValidationException> fieldExceptions;

    public FieldsValidationException(List<FieldValidationException> fieldExceptions) {
        super(null, null);
        this.fieldExceptions = fieldExceptions;
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


    public void setErrorAttributes(HttpServletRequest request) {

        for (FieldValidationException fieldValidationException : fieldExceptions) {
            fieldValidationException.setErrorAttribut(request);   
        }
    }
}
