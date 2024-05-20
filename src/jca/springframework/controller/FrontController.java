package jca.springframework.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.MethodScanner;
import jca.springframework.PackageScanner;
import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.annotations.Controller;
import jca.springframework.annotations.Get;
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
        out.println("URL : "+req.getRequestURL().toString());
        this.printControllers(out);
    }

/// Fonctionalites
    public void scann_controllers(){
        /// Recuperer la liste de tous les controllers du contexte
        List<Class<?>> controllersClasses =  PackageScanner.findAnnotedClasses(
                                            getController_package(),
                                Controller.class 
                                        );
        for (Class<?> controller : controllersClasses) {
            Method[] controllerMethods = controller.getDeclaredMethods();
            for (Method method : controllerMethods) {
                Get getConfig =  MethodScanner.getGetAnnotation(method);
                if ( getConfig != null) {
                    ///  Creation de l'objet mapping controller -> method 
                    Mapping mapping = new Mapping(controller.getName(),method.getName());
                    /// Ajouter a la liste de url mapping correspondant
                    getUrlMapping().put(getConfig.url(),mapping);
                }
            }
        }
        
    }
    private void printControllers(PrintWriter out){
        out.println("PACKAGE : "+this.getController_package());
        out.println("CONTROLLERS :");
        Set<String> urls = getUrlMapping().keySet();
        for (String url : urls) {
            out.println("\t-"+getUrlMapping().get(url));
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