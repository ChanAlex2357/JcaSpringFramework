package jca.springframework.view;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StringView extends View{
    String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public StringView(String content){
        /// La view ne redirige pas vers une vue
        super(null);
        setContent(content);
    }
    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /// Afficher le contenue
        resp.getWriter().println(getContent());
        resp.getWriter().flush();
    }
}
