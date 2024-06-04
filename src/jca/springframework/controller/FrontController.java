package jca.springframework.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.UrlMapping;
import jca.springframework.exception.FrameworkException;
import jca.springframework.scanner.exception.NotControllerPackageException;
import jca.springframework.view.ExceptionView;
import jca.springframework.view.View;
import jakarta.servlet.http.HttpServletRequest;
/**
 * FrontController
 * Joue le role du servlet principale qui va recuperer tout les requetes entrantes
 */
public class FrontController extends HttpServlet{
    /// Le package des controllers
    private String controller_package;
    /// Mapping des controller
    private HashMap<String,Mapping> urlMapping;

    private List<FrameworkException> initExceptions;

    List<FrameworkException> getInitExceptions() {
        return initExceptions;
    }
    void setInitExceptions(List<FrameworkException> initExceptions) {
        this.initExceptions = initExceptions;
    }
    @Override
    public void init() throws ServletException {
        super.init();
        setInitExceptions(new ArrayList<>());
        /// Recuperer le nom de package des controller 
        this.setController_package(getServletConfig().getInitParameter("package-name"));
        /// Iitialiser la liste a 0
        this.setUrlMapping(new HashMap<String,Mapping>());
        /// Scanner la liste des controllers
        this.scann_controllers();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        if ( getInitExceptions().size() == 0) {
            /// Recuperer l'url demander
            String fullUrl = req.getRequestURL().toString();
            /// Excecution du mapping correspondant a l'url
            executeMapping(fullUrl,req,resp);    
        }
        /// Si il y a des exceptions a l'init on afficher les erreurs
        else {
            View excptionView = new ExceptionView(getInitExceptions());
            excptionView.dispatch(req, resp);
        }
        
    }

/// Fonctionalites
    public void executeMapping(String fullurl,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        View viewResult = null;
        try {
            /// Recuperer le mapping associer a l'url demander
            Mapping mapping = UrlMapping.getMappingWithFullUrl(fullurl,getUrlMapping());
            viewResult = mapping.getViewResult();
        } catch (FrameworkException e) {
            viewResult = e.getExceptionView();
        }
        
        viewResult.dispatch(req, resp);
    }
    private void scann_controllers(){
        try {
            MappingBuilder.scann_controllers(
                getController_package(),
                getUrlMapping()
            );
            /// Si le Url Mapping reste null alors il n'y a aucun controller
            if (getUrlMapping() == null) {
                throw new NotControllerPackageException(getController_package());
            }
        } catch (FrameworkException e) {
            getInitExceptions().add(e);
        }
    }
/// Getteurs et Setteurs
    public String getController_package() {
        return controller_package;
    }
    public void setController_package(String controller_package) {
        this.controller_package = controller_package;
    }
    public HashMap<String, Mapping> getUrlMapping() {
        return urlMapping;
    }
    public void setUrlMapping(HashMap<String, Mapping> urlMapping) {
        this.urlMapping = urlMapping;
    }
}