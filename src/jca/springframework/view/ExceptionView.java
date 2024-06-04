package jca.springframework.view;

import java.util.List;

import jca.springframework.exception.FrameworkException;

public class ExceptionView extends StringView{
    public ExceptionView(FrameworkException exception){
        super(getExceptionContent(exception));
    }
    public ExceptionView(List<FrameworkException> exceptions){
        super(getExceptionContent(exceptions));
    }

    private static String getExceptionContent(FrameworkException exception){
        return "\n[!! ERROR !!]\n"+exception.getMessage();
    }
    private static String getExceptionContent(List<FrameworkException> exceptions){
        String message = "";

        for ( FrameworkException exception : exceptions) {
            message += getExceptionContent(exception);
        }
        return message;
    }
}
