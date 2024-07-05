package jca.springframework.session;

import jakarta.servlet.http.HttpSession;

public class WebSession {
    private HttpSession session;

    WebSession(HttpSession session){
        setSession(session);
    }
    public HttpSession getSession() {
        return this.session;
    }
    void setSession(HttpSession session){
        this.session = session;
    }
// Fuctions
    public void add(String key, Object value){
        getSession().setAttribute(key, value);
    }
    public void remove(String key){
        getSession().removeAttribute(key);
    }
    public Object get(String key) {
        return getSession().getAttribute(key); 
    }
}