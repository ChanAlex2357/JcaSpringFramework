package jca.springframework.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.UrlMapping;
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
        PrintWriter out = resp.getWriter();
        String fullUrl = req.getRequestURL().toString();
        out.println("URL : "+fullUrl);
        this.printController(
            fullUrl,
            UrlMapping.getMappingWithFullUrl(
                fullUrl,
                getUrlMapping()
            ),
            out
        );
    }

/// Fonctionalites
    
    public void scann_controllers(){
        MappingBuilder.scann_controllers(
            getController_package(),
            getUrlMapping()
        );
    }
    
    void printControllers(PrintWriter out){
        out.println("PACKAGE : "+this.getController_package());
        out.println("CONTROLLERS :");
        Set<String> urls = getUrlMapping().keySet();
        for (String url : urls) {
            printController(
                url,
                out
            );
        }
    }
    void printController(String url , PrintWriter out){
        printController(
            url,
            UrlMapping.getMapping(url, getUrlMapping()),
            out
        );
    }
    void printController(String url , Mapping mapping , PrintWriter out ){
        if (mapping == null) {
            System.out.println("\t Aucun mapping ne correspond a l'url :"+url);
        }
        else {
            out.println("\t-("+url+") "+mapping);
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