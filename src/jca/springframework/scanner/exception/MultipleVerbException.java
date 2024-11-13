package jca.springframework.scanner.exception;

import java.lang.reflect.Method;

import jca.springframework.exception.FrameworkException;

public class MultipleVerbException extends FrameworkException{
    public MultipleVerbException (Method method){
        super(errorMessage(method) , null);
    }

    protected static String errorMessage(Method method) {
        return "La methode "+method.getName()+" possede multiple declaration de metnod \"GET\" et \"POST\"";
    }
}