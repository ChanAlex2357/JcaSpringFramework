package jca.springframework.view;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.exception.FrameworkException;

public class ExceptionView extends StringView{
    private int status = 500;
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

    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(this.getStatus());
        super.dispatch(req, resp);
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
