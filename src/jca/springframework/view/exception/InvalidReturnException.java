package jca.springframework.view.exception;

import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.VerbAction;

public class InvalidReturnException extends FrameworkException{
    
    public InvalidReturnException(VerbAction mapping){
        super(errorMessage(mapping),null);
    }
    static private String errorMessage(VerbAction mapping){
        return "La methode de controller suivante ne possede pas un type retour valide( VOIR LE README.md )\n METHODE: "+mapping.getClassMethode().getMethodeControllerName()+"\nCONTROLLER: "+mapping.getClassMethode().getClassControllerName()+"\n";
    }
}
