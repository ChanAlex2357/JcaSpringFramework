package jca.springframework.view;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class View {
    HashMap<String,Object> data;
    String viewPath;
    public View(String viewPath){
        intView(viewPath,new HashMap<>());
    }

    private void intView(String viewPath,HashMap<String,Object> data){
        setViewPath(viewPath);
        setData(data);
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

    
    
    abstract public void dispatch(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException;
    
    protected void setAttributs(HttpServletRequest req){
        /// Ajouter en attribut les objets de la vue
        for (String attributName : getData().keySet()) {
            req.setAttribute(attributName, getData().get(attributName));
        }
    }
}
