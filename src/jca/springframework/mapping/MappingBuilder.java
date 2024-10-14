package jca.springframework.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import jca.springframework.annotations.classe.Controller;
import jca.springframework.controller.exception.DuplicateUrlException;
import jca.springframework.scanner.PackageScanner;
import jca.springframework.scanner.exception.InvalidPackageException;

public class MappingBuilder {
    static public HashMap<String,Mapping> scann_controllers(String controllerPackage)throws InvalidPackageException, DuplicateUrlException {
        HashMap<String,Mapping> urlMapping = new HashMap<String,Mapping>();
        scann_controllers(controllerPackage,urlMapping);
        return urlMapping; 
    }
    static public void scann_controllers(String controllerPackage , HashMap<String,Mapping> urlMapping)throws InvalidPackageException, DuplicateUrlException {
        Mapping mapping = null;
        /// Recuperer la liste de tous les controllers du contexte
        List<Class<?>> controllersClasses = PackageScanner.findAnnotedClasses(controllerPackage,Controller.class );
        /// Traitement de chaque classe de controller
        for (Class<?> controller : controllersClasses) {
            /// Recuperation des methodes de controller
            Method[] controllerMethods = controller.getDeclaredMethods();
            /// Traitement de chaque methode de controller
            for (Method method : controllerMethods) {
                MappingClassMethode mappingClassMethode = createMapping(controller , method);
                String url = mappingClassMethode.getMappingAnnotation().getUrl();
                /*
                    * Verification de l'etat de l'url
                    * - Si l'url est null alors la methode n'est pas une methode de control de requete
                    * - Si l'url existe deja dans la liste des url mapping alors il y a duplication de control de requete dans le controller 
                */
                if (url == null) {break;}
                mapping = urlMapping.get(url);
                if(mapping == null){
                    mapping = new Mapping(url,mappingClassMethode);

                }
                else if (mapping != null) {
                    boolean added = mapping.getVerbMapping().add(mappingClassMethode);
                    if (!added) {
                        // Exception de methode identique
                    }
                }
                // if(urlMapping.get(url) != null){throw new DuplicateUrlException(url,urlMapping.get(url),mapping);}
                
                urlMapping.put(url,mapping);
            }
        }
    }

    static public MappingClassMethode createMapping(Class<?> controller , Method method){
        MappingAnnotation mappingAnnotation = new MappingAnnotation(method);
        MappingClassMethode mapping;
        ///  Creation de l'objet mapping controller -> method 
        mapping = new MappingClassMethode(
            mappingAnnotation,   
            controller.getName(),       // Le nom du controller
            method                      // La methode a appeler
        );
        return mapping;
    } 
}
