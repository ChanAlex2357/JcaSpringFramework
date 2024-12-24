package jca.springframework.view;

import jca.springframework.scanner.ValidationScanner;

public class ValidationView extends ModelAndView{
    public ValidationView(ValidationScanner validationScanner , ModelAndView mv) {
        super(mv.getError());
        // Passer les attributs de validation dans les data de la view
        
    }
}
