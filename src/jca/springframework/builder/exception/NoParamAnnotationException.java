package jca.springframework.builder.exception;

import java.lang.reflect.Parameter;

import jca.springframework.exception.FrameworkException;

public class NoParamAnnotationException extends FrameworkException{
    Parameter parameter;
    public NoParamAnnotationException(Parameter parameter,Exception err){
        super(err);
        setParameter(parameter);
    }
    @Override
    public String getMessage() {
        return ("[ ETU 002434 ] : Le parametre "+this.getParameter().getName()+" n'est pas annoter correctement ; En tant que parametre d'action il dois posseder un annotation jca.springframework.annotations.parameter.Param");
    }
    public Parameter getParameter() {
        return parameter;
    }
    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
