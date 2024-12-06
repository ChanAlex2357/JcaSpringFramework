package jca.springframework.view;

import jca.springframework.builder.exception.FieldsValidationException;

public class ValidationView extends ModelAndView{
    public ValidationView(FieldsValidationException validationException , ModelAndView mv) {
        super(mv.getError());
        // Passer les attributs de validation dans les data de la view
        validationException.setErrorAttributes(this);
    }
}
