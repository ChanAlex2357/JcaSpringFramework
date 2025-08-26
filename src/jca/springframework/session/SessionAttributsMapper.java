package jca.springframework.session;

import java.util.HashMap;
import java.util.Map;

public class SessionAttributsMapper {
    public String SESSION_ID ;
    HashMap<String,Object> attributs;

    public SessionAttributsMapper(String id){
        setSESSION_ID(id);
        setAttributs(new HashMap<>());
    }

    public HashMap<String, Object> getAttributs() {
        return attributs;
    }

    public void setAttributs(HashMap<String, Object> fields) {
        this.attributs = fields;
    }

    public String getSESSION_ID() {
        return SESSION_ID;
    }

    public void setSESSION_ID(String sESSION_ID) {
        SESSION_ID = sESSION_ID;
    }

    public void add(String name, Object value){
        getAttributs().put(name, value);
    }

    public void addAll(Map<String,Object> data){
        getAttributs().putAll(data);
    }
}
