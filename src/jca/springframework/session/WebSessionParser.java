package jca.springframework.session;

import java.util.Enumeration;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class WebSessionParser {
    static public WebSession HttpSessionToWebSession(HttpServletRequest request){
        return HttpSessionToWebSession(request.getSession(true));
    }
    static public WebSession HttpSessionToWebSession(HttpSession session){
        WebSession webSession = new WebSession();
        // Transferer les <key,value> de la session vers  websession
        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            webSession.getSessionValues().put(key, session.getAttribute(key));
        }
        session.setAttribute(null, keys);
        return webSession;
    }

    static public void WebSessionToHttpSession(WebSession webSession , HttpServletRequest request) {
        WebSessionToHttpSession(webSession, request.getSession());
    }
    static public void WebSessionToHttpSession(WebSession webSession , HttpSession session) {

        Set<String> keys = webSession.getSessionValues().keySet();
        for (String key : keys) {
            Object webSessionValue = webSession.get(key);
            if (webSessionValue == null) {
                session.removeAttribute(key);
                continue;
            }
            session.setAttribute(key, webSessionValue );
        }
            
    }
}
