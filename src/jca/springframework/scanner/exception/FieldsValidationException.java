package jca.springframework.scanner.exception;

import java.util.List;

public class FieldsValidationException extends Exception {
    private final List<FieldValidationException> fieldExceptions;

    public FieldsValidationException(List<FieldValidationException> fieldExceptions) {
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
}
