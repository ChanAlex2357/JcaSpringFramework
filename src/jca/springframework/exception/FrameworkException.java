package jca.springframework.exception;

import jca.springframework.view.ExceptionView;
import jca.springframework.view.View;

public abstract class FrameworkException extends Exception{
    int exception_status = 500;
    public  FrameworkException(){
        super(null,null);
    }
    public FrameworkException( String message , Exception source){
        super(message, source);
    }
    public FrameworkException(int status , String message , Exception source){
        super(message, source);
        setException_status(status);   
    }
    public FrameworkException( Exception source){
        super(null, source);
    }
    public FrameworkException(int status , Exception source){
        super(null, source);
        setException_status(status);   
    }
    public void setException_status(int exception_status) {
        this.exception_status = exception_status;
    }
    public int getException_status() {
        return exception_status;
    }
    protected static String errorMessage(String message){
        return message;
    }
    public View getExceptionView(){
        ExceptionView view = new ExceptionView(this);
        return view;
    }

    abstract public String getMessage();
}
