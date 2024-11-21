package jca.springframework.view;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.builder.exception.FieldsValidationException;

public class ValidationRefererView extends ReferView{
    public ValidationRefererView(HttpServletRequest req , FieldsValidationException validationException) {
        super(req);
        // Passer les attributs de validation dans les data de la view
        validationException.setErrorAttributes(this);
    }
}
