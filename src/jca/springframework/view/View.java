package jca.springframework.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.builder.exception.FieldsValidationException;
import jca.springframework.session.FieldsValidations;
import jca.springframework.session.RedirectAttributs;
import jca.springframework.session.WebSession;
import jca.springframework.session.WebSessionParser;
import jca.springframework.utils.RequestUtils;
import jca.springframework.utils.StringUtils;

public abstract class View {
    public RedirectAttributs redirectData = new RedirectAttributs();
    public FieldsValidationException fieldsValidationException;
    HashMap<String,Object> data;
    String viewPath;
    public View(String viewPath){
        intView(viewPath,new HashMap<>());
    }
    private void intView(String viewPath,HashMap<String,Object> data){
        setViewPath(viewPath);
        setData(data);
    }

    protected void setViewPathAsControllerUrl(){
        setViewPath(StringUtils.getControllerUrl(this.getViewPath()));
    }
    public HashMap<String, Object> getData() {
        return data;
    }
    void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    public String getViewPath() {
        return viewPath;
    }
    void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }
    public void addObject(String name,Object value){
        getData().put(name, value);
    }
    public Object getObject(String name){
        return getData().get(name);
    }
    abstract public void dispatch(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException;
    
    protected void setAttributs(HttpServletRequest req){
        RequestUtils.setAttributs(req, data);
        
        WebSession session = WebSessionParser.HttpSessionToWebSession(req);
        session.add(new RedirectAttributs().getSESSION_ID(), getRedirectData());
        if (fieldsValidationException != null) {
            session.add(new FieldsValidations().getSESSION_ID(), fieldsValidationException.getFieldsValidations());
        }
    }
    public void addRedirectAttribut(String name, Object value){
        getRedirectData().add(name,value);
    }

    public void addAllRedirectAttribut(Map<String,Object> data){
        getRedirectData().addAll(data);
    }
    public RedirectAttributs getRedirectData() {
        return redirectData;
    }

    public void setRedirectData(RedirectAttributs redirectData) {
        this.redirectData = redirectData;
    }
    public FieldsValidationException getFieldsValidationException() {
        return fieldsValidationException;
    }
    public void setFieldsValidationException(FieldsValidationException fieldsValidationException) {
        this.fieldsValidationException = fieldsValidationException;
    }
}
