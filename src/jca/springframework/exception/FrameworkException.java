package jca.springframework.exception;

import jca.springframework.view.ExceptionView;
import jca.springframework.view.StringView;
import jca.springframework.view.View;

public class FrameworkException extends Exception{
    public FrameworkException( String message , Exception source){
        super(errorMessage(message), source);
    }

    private static String errorMessage(String message){
        return message;
    }
    public View getExceptionView(){
        return new ExceptionView(this);
    }
}
