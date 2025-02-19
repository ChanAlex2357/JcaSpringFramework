package jca.springframework.view.exception;

import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.VerbAction;

public class InvalidReturnException extends FrameworkException {

    private final VerbAction mapping;
    private final Object resulObject;

    public InvalidReturnException(VerbAction mapping,Object returnedObject) {
        super(null); // Définit un code d'état 500 pour une configuration incorrecte
        this.mapping = mapping;
        this.resulObject = returnedObject;
    }

    @Override
    public String getMessage() {
        return "La méthode du contrôleur suivante ne possède pas un type de retour valide (voir README.md).\n" +
               "Méthode : " + mapping.getClassMethode().getMethodeControllerName() + "\n" +
               "Contrôleur : " + mapping.getClassMethode().getClassControllerName() + "\n" +
               "Resultat : "+resulObject;
    }

    public VerbAction getMapping() {
        return mapping;
    }
}
