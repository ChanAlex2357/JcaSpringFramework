package jca.springframework.scanner;

import java.lang.reflect.Method;
import java.util.List;

import jca.springframework.annotations.classe.Controller;
import jca.springframework.builder.MappingBuilder;
import jca.springframework.controller.exception.DuplicateUrlException;
import jca.springframework.mapping.AdminUrlMapping;
import jca.springframework.mapping.Mapping;
import jca.springframework.scanner.exception.InvalidPackageException;
import jca.springframework.scanner.exception.MultipleVerbException;

public class ControllerScanner {
    private String scannLog;
    AdminUrlMapping adminUrlMapping ;
    MappingBuilder mappingBuilder = new MappingBuilder(getAdminUrlMapping());
    public ControllerScanner(){
        setAdminUrlMapping(new AdminUrlMapping());
        setMappingBuilder();
    }
    public ControllerScanner(AdminUrlMapping adminUrlMapping){
        setAdminUrlMapping(adminUrlMapping);
        setMappingBuilder();
    }
    public void scann_controllers(String controllerPackage)throws InvalidPackageException, DuplicateUrlException, MultipleVerbException {
        /// Recuperer la liste de tous les controllers du contexte
        List<Class<?>> controllersClasses = PackageScanner.findAnnotedClasses(controllerPackage,Controller.class );
        addToLog("Finding controller Class :"+controllersClasses);
        /// Traitement de chaque classe de controller
        for (Class<?> controller : controllersClasses) {
            /// Recuperation des methodes de controller
            addToLog("Traitement . . . "+controller.getName());
            Method[] controllerMethods = controller.getDeclaredMethods();
            /// Traitement de chaque methode de controller
            for (Method method : controllerMethods) {
                Mapping mapping =getMappingBuilder().buildMapping(controller, method);
                String log = "Methode :" +method.getName()+" {";
                String etat = "YES";
                if (mapping == null) {
                    etat = "NO";
                }
                addToLog(log+etat+" }");
            }
        }
    }
    public MappingBuilder getMappingBuilder() {
        return mappingBuilder;
    }
    public void setMappingBuilder(MappingBuilder mappingBuilder) {
        this.mappingBuilder = mappingBuilder;
    }
    public AdminUrlMapping getAdminUrlMapping() {
        return adminUrlMapping;
    }
    public void setAdminUrlMapping(AdminUrlMapping adminUrlMapping) {
        this.adminUrlMapping = adminUrlMapping;
    }
    public void setMappingBuilder(){
        setMappingBuilder(new MappingBuilder(getAdminUrlMapping()));
    }
    
    public String getScannLog() {
        return scannLog;
    }
    public void setScannLog(String scannLog) {
        this.scannLog = scannLog;
    }

    public void addToLog(String log){
        setScannLog( getScannLog()+"\n> "+log +"\n");
    }
}
