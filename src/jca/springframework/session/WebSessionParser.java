package jca.springframework.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class WebSessionParser {
    static public WebSession HttpSessionToWebSession(HttpServletRequest request){
        return HttpSessionToWebSession(request.getSession(true));
    }
    static public WebSession HttpSessionToWebSession(HttpSession session){
        WebSession webSession = new WebSession(session);
        return webSession;
    }
}
