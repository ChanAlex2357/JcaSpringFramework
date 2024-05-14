package jca.springframework.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.PackageScanner;
import jakarta.servlet.http.HttpServletRequest;
/**
 * FrontController
 * Joue le role du servlet principale qui va recuperer tout les requetes entrantes
 */
public class FrontController extends HttpServlet{
    /// Le package des controllers
    private String controller_package;
    private boolean scann_status;
    private List<String> controllers;

    
    @Override
    public void init() throws ServletException {
        super.init();
        /// Recuperer le nom de package des controller 
        this.setController_package(getServletConfig().getInitParameter("package-name"));
        /// Considerer que le scan est encore a faire 
        this.setScann_status(false);
        /// Initialiser la liste des controllers a 0 
        this.setControllers(new ArrayList<String>());
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
        this.scann_controllers();
        PrintWriter out = resp.getWriter();
        out.println("URL : "+req.getRequestURL().toString());
        this.printControllers(out);
    }

/// Fonctionalites
    public List<String> scann_controllers(){
        if (!scann_status) {
            setControllers(PackageScanner.findClassesNames(this.getController_package()));
        }
        
        return this.getControllers();
    }
    private void printControllers(PrintWriter out){
        List<String> list  = this.getControllers();
        out.println("CONTROLLERS :");
        for (String controller : list) {
            out.println("\t- "+controller);
        }

    }
/// Getteurs et Setteurs
    public String getController_package() {
        return controller_package;
    }
    public void setController_package(String controller_package) {
        this.controller_package = controller_package;
    }
    public boolean isScann_status() {
        return scann_status;
    }
    public void setScann_status(boolean scann_status) {
        this.scann_status = scann_status;
    }
    public List<String> getControllers() {
        return controllers;
    }
    public void setControllers(List<String> controllers) {
        this.controllers = controllers;
    }
}