package jca.springframework.session;

import java.util.Enumeration;

import jakarta.servlet.http.HttpSession;

public class WebSessionParser {
    WebSession HttpSessionToWebSession(HttpSession session){
        WebSession webSession = new WebSession();
        // Transferer les <key,value> de la session vers  websession
        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            webSession.getSessionValues().put(key, session.getAttribute(key));
        }
        return webSession;
    }
}
