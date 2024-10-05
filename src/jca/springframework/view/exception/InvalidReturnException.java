package jca.springframework.view.exception;

import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.Mapping;

public class InvalidReturnException extends FrameworkException{
    
    public InvalidReturnException(Mapping mapping){
        super(errorMessage(mapping),null);
    }

    static private String errorMessage(Mapping mapping){
        return "La methode de controller suivante ne possede pas un type retour valide( VOIR LE README.md )\n METHODE: "+mapping.getMethodeControllerName()+"\nCONTROLLER: "+mapping.getClassControllerName()+"\n";
    }
}
