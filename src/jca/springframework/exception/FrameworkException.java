package jca.springframework.exception;

import jca.springframework.view.ExceptionView;
import jca.springframework.view.View;

public class FrameworkException extends Exception{
    int exception_status;
    public FrameworkException( String message , Exception source){
        super(errorMessage(message), source);
        setException_status(500);
    }
    public FrameworkException(int status , String message , Exception source){
        super(errorMessage(message), source);
        setException_status(status);   
    }
    public void setException_status(int exception_status) {
        this.exception_status = exception_status;
    }
    public int getException_status() {
        return exception_status;
    }
    private static String errorMessage(String message){
        return message;
    }
    public View getExceptionView(){
        ExceptionView view = new ExceptionView(this); 
        view.setStatusCode(this.getException_status());
        return view;
    }
}
