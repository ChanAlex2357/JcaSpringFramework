package jca.springframework.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import jca.springframework.annotations.Controller;
import jca.springframework.annotations.Get;
import jca.springframework.controller.exception.DuplicateUrlException;
import jca.springframework.scanner.MethodScanner;
import jca.springframework.scanner.PackageScanner;
import jca.springframework.scanner.exception.InvalidPackageException;

public class MappingBuilder {

    static public HashMap<String,Mapping> scann_controllers(String controllerPackage)throws InvalidPackageException, DuplicateUrlException {
        HashMap<String,Mapping> urlMapping = new HashMap<String,Mapping>();
        scann_controllers(controllerPackage,urlMapping);
        return urlMapping; 
    }
    
    static public void scann_controllers(String controllerPackage , HashMap<String,Mapping> urlMapping)throws InvalidPackageException, DuplicateUrlException {
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
                    String url = getConfig.url();
                    if( urlMapping.get(url) != null){
                        throw new DuplicateUrlException(url,urlMapping.get(url),mapping);
                    } 
                    urlMapping.put(url,mapping);
                }
            }
        }
    }
}
