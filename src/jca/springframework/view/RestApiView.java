package jca.springframework.view;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class RestApiView extends View{
    private final String jsonAttributeName = "jsonString";
    

    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        resp.getWriter().println(getJsonResponse());
    }

    public String getJsonResponse() {
        return this.getObject(getJsonAttributeName()).toString() ;
    }

    public void setJsonResponse(String jsonResponse) {
        addObject(getJsonAttributeName(), jsonResponse);
    }
    
    public String getJsonAttributeName() {
        return jsonAttributeName;
    }

    public RestApiView(String json) {
        super(null);
        
        setJsonResponse(json);
    }
}   
