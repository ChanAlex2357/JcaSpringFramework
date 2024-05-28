package jca.springframework.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import jca.springframework.annotations.Controller;
import jca.springframework.annotations.Get;
import jca.springframework.scanner.MethodScanner;
import jca.springframework.scanner.PackageScanner;

public class MappingBuilder {

    static public HashMap<String,Mapping> scann_controllers(String controllerPackage){
        HashMap<String,Mapping> urlMapping = new HashMap<String,Mapping>();
        scann_controllers(controllerPackage,urlMapping);
        return urlMapping; 
    }
    
    static public void scann_controllers(String controllerPackage , HashMap<String,Mapping> urlMapping){
        /// Recuperer la liste de tous les controllers du contexte
        List<Class<?>> controllersClasses = PackageScanner.findAnnotedClasses(
                                                controllerPackage,
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
                    urlMapping.put(getConfig.url(),mapping);
                }
            }
        }
    }
}
