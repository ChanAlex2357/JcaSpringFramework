package jca.springframework.view;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ModelAndView extends View{
    public ModelAndView (String viewPath){
        super(viewPath);
    }
    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /// Ajouter les donnees data en attribut de la requete
        setAttributs(req);
        /// Dispatch vers la view demander
        req.getRequestDispatcher( getViewPath() ).forward(req,resp);
    }
}
