package jca.springframework.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import jca.springframework.annotations.classe.Controller;
import jca.springframework.annotations.MappingAnnotation;
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
                MappingAnnotation mappingAnnotation = MethodScanner.geMappingAnnotation(method);
                Mapping mapping;
                
                /*
                * Verification de l'etat de l'url
                * - Si l'url est null alors la methode n'est pas une methode de control de requete
                * - Si l'utl existe deja dans la liste des url mapping alors il y a duplication de control de requete dans le controller 
                */
                if( mappingAnnotation == null){
                    break;
                }
                String url = mappingAnnotation.getUrl();
                ///  Creation de l'objet mapping controller -> method 
                mapping = new Mapping(
                    controller.getName(), // Le nom du controller
                    method,               // La methode a appeler
                    mappingAnnotation
                );
                if(urlMapping.get(url) != null){
                    throw new DuplicateUrlException(url,urlMapping.get(url),mapping);
                }
                urlMapping.put(url,mapping);
            }
        }
    }
}
