package jca.springframework.view;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.exception.FrameworkException;

public class ExceptionView extends StringView{
    FrameworkException exceptionSource;
    public ExceptionView(FrameworkException exception){
        super(generateErrorHtml(exception));
        setExceptionSource(exception);
    }
    private static String generateErrorHtml(FrameworkException exception) {
        // Construire le code HTML avec le message d'erreur et le code de statut
        StringBuilder htmlBuilder = new StringBuilder();
        
        htmlBuilder.append("<html><head><style>")
                   .append("body { font-family: Arial, sans-serif; margin: 20px; }")
                   .append("h3 { color: red; }")
                   .append("div { border: 1px solid #ccc; padding: 10px; background-color: #f9f9f9; }")
                   .append("b { color: #333; }")
                   .append("</style></head><body>");
        
        htmlBuilder.append("<h3> ERROR - ").append(exception.getException_status()).append("</h3>")
                   .append("<div>")
                   .append(generateExceptionMessage(exception,0))
                   .append("</div>");
        
        htmlBuilder.append("</body></html>");
        
        return htmlBuilder.toString();
    }

    private static String generateExceptionMessage( FrameworkException fe , int num){
        String message = "";
        if (num > 0) {
            message = "<b>[ERROR MESSAGE] -- "+num+":</b>";
        }
        else {
            message = "<b>[ERROR MESSAGE] :</b>";
        }
        message += " <br> \n<p>"+fe.getMessage()+"</p>";
        return message;
    }

    public int getStatusCode() {
        return getExceptionSource().getException_status();
    }
    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(getStatusCode());
        resp.getWriter().flush();
        super.dispatch(req, resp);
    }
    public FrameworkException getExceptionSource() {
        return exceptionSource;
    }
    public void setExceptionSource(FrameworkException exceptionSource) {
        this.exceptionSource = exceptionSource;
    }
}
