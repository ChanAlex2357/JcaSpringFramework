package jca.springframework.exception;

import jca.springframework.view.StringView;
import jca.springframework.view.View;

public class FrameworkException extends Exception{
    public FrameworkException( String message , Exception source){
        super(message, source);
    }

    public View getExceptionView(){
        return new StringView(getMessage());
    }
}
