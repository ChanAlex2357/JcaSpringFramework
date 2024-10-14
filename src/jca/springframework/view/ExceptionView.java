package jca.springframework.view;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.exception.FrameworkException;

public class ExceptionView extends StringView{
    private int statusCode = 500;
    public ExceptionView(FrameworkException exception){
        super(getExceptionContent(exception));
    }
    public ExceptionView(List<FrameworkException> exceptions){
        super(getExceptionContent(exceptions));
    }

    private static String getExceptionContent(FrameworkException exception){
        return "\n[!! ERROR !!]\n"+exception.getMessage();
    }
    public static String prepareExceptionBody(FrameworkException exception){
        return "<h1></h1>";
    }
    private static String getExceptionContent(List<FrameworkException> exceptions){
        String message = "";

        for ( FrameworkException exception : exceptions) {
            message += getExceptionContent(exception);
        }
        return message;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public int getStatusCode() {
        return statusCode;
    }
    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(getStatusCode());
        resp.getWriter().println("STATUS CODE -  ["+this.getStatusCode()+"]");
        resp.getWriter().flush();
        super.dispatch(req, resp);
    }
}
