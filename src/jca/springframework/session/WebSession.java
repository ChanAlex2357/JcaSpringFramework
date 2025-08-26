package jca.springframework.session;

import java.util.HashMap;
import java.util.Map;

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
    public void addAll(Map<String, Object> maps){
        for (String attributName : maps.keySet()) {
            add(attributName, maps.get(attributName));
        }
    }

    public Object pop(String key){
        Object data = get(key);
        if (data == null) {
            return null;
        }
        remove(key);
        return data;
    }
}