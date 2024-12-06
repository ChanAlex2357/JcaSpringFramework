package jca.springframework.view;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.scanner.ValidationScanner;

public class ModelAndView extends View{
    public ModelAndView (String viewPath){
        super(viewPath);
    }
    public ModelAndView (String viewPath , String error){
        super(viewPath);
        setError(error);
    }

    public View checkError(ValidationScanner validationScanner){
        if (validationScanner.isValidationErrorPresent()){
            return new ValidationView(validationScanner.getValidationExceptions(),this);
        }
        return null;
    }

    public View getAsView(ValidationScanner validationScanner){
        View v = null;
        if (validationScanner != null) {
            v = checkError(validationScanner);
        }
        if (v == null) {
            return this;
        }
        return v;
    }
    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /// Ajouter les donnees data en attribut de la requete
        setAttributs(req);
        /// Dispatch vers la view demander
        req.getRequestDispatcher( getViewPath() ).forward(req,resp);
    }
}
