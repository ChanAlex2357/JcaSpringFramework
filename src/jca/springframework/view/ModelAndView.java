package jca.springframework.view;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ModelAndView extends View{
    private String error;
    public ModelAndView (String viewPath){
        super(viewPath);
    }
    public ModelAndView (String viewPath , String error){
        super(viewPath);
        setError(error);
    }
    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /// Ajouter les donnees data en attribut de la requete
        setAttributs(req);
        /// Dispatch vers la view demander
        req.getRequestDispatcher( getViewPath() ).forward(req,resp);
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
