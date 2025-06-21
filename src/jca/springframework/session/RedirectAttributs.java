package jca.springframework.session;

import java.util.HashMap;
import java.util.Map;

public class RedirectAttributs {
    public static final String SESSSION_ID = "redirect_attributs";
    HashMap<String,Object> redirectData;
    public RedirectAttributs(){
        setRedirectData(new HashMap<>());
    }

    public HashMap<String, Object> getRedirectData() {
        return redirectData;
    }
    public void setRedirectData(HashMap<String, Object> redirectData) {
        this.redirectData = redirectData;
    }

    public void add(String name, Object value){
        getRedirectData().put(name, value);
    }

    public void addAll(Map<String,Object> data){
        getRedirectData().putAll(data);
    }
}
