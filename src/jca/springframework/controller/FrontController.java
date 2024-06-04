package jca.springframework.controller;
import java.io.IOException;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.UrlMapping;
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

    @Override
    public void init() throws ServletException {
        super.init();
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
        /// Recuperer l'url demander
        String fullUrl = req.getRequestURL().toString();
        /// Excecution du mapping correspondant a l'url
        executeMapping(fullUrl,req,resp);
    }

/// Fonctionalites
    public void executeMapping(String fullurl,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        /// Message a afficher ; par defaut indiquant que l'url n'est associer a aucun controller
        View viewResult = ControllerPrinter.errorMappingView(fullurl);
        /// Recuperer le mapping associer a l'url demander
        Mapping mapping = UrlMapping.getMappingWithFullUrl(fullurl,getUrlMapping());
        if (mapping != null) {
            /// Excuter le mapping et recuperer le String de retour
            viewResult = mapping.getViewResult();
        }
        viewResult.dispatch(req, resp);
    }
    public void scann_controllers(){
        MappingBuilder.scann_controllers(
            getController_package(),
            getUrlMapping()
        );
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