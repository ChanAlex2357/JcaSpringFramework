package jca.springframework.session;
import java.util.HashMap;

public class WebSession {
    private HashMap<String,Object> sessionValues;

    WebSession(){
        setSessionValues(new HashMap<String,Object>());
    }
    public HashMap<String, Object> getSessionValues() {
        return sessionValues;
    }

    private void setSessionValues(HashMap<String, Object> sessionValues) {
        this.sessionValues = sessionValues;
    }

// Fuctions

    public void add(String key, Object value){

    }
    public void remove(String key){

    }
    public Object get(String key){
        Object result = getSessionValues().get(key);
            
        if (result == null) {
            // Exception pour un valeur qui n'existe pas
        }
        return result; 
    }
    public void update(String key , Object value){

    }
    
}
