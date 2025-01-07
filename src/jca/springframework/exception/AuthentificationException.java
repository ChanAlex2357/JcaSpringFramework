package jca.springframework.exception;

import jca.springframework.mapping.VerbAction;

public class AuthentificationException extends FrameworkException{

    VerbAction verbAction;

    public AuthentificationException(VerbAction verbAction) {
        setVerbAction(verbAction);
    }
    public VerbAction getVerbAction() {
        return verbAction;
    }
    public void setVerbAction(VerbAction verbAction) {
        this.verbAction = verbAction;
    }

    @Override
    public String getMessage() {
        return "Vous ne disposer pas des droits necessaire pour executer cette methode . Vous devez vous authentifiez en tant que : "+verbAction.getRoleAccess();
    }
}
