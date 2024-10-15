package jca.springframework.mapping;

import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.controller.exception.RequestMethodCallException;
import jca.springframework.exception.FrameworkException;
import jca.springframework.view.View;

public class Mapping {
    private String url;
    private Set<VerbAction> verbMapping ;
    
    public Mapping(String url){
        setUrl(url);
        setVerbMapping(new HashSet<VerbAction>());
    }
    public Mapping(String url,VerbAction mappingClassMethode){
        setUrl(url);
        setVerbMapping(new HashSet<VerbAction>());
        getVerbMapping().add(mappingClassMethode);
    }

    public void addMappingClassMethode(VerbAction mappingClassMethode){
        getVerbMapping().add(mappingClassMethode);
    }
    public Set<VerbAction> getVerbMapping() {
        return verbMapping;
    }
    public void setVerbMapping(Set<VerbAction> verbMapping) {
        this.verbMapping = verbMapping;
    }
    public View getViewResult(HttpServletRequest req) throws IllegalArgumentException, InstantiationException, FrameworkException {
        // Verification de la conformite de la methode utiliser pour l'appel de la methode de controller
        VerbAction mappingClassMethode = this.getMappingClassMethode(req.getMethod());
        // Recuperer le resultat de la requete
        return mappingClassMethode.getViewResult(req);
        
    }
    public VerbAction getMappingClassMethode(String requestMethod) throws RequestMethodCallException {
        VerbAction mappingCorrespondance = null;
        for (VerbAction mappingClassMethode : verbMapping) {
            if ( mappingClassMethode.getVerb().equals(requestMethod)) {
                mappingCorrespondance = mappingClassMethode;
            }
        }

        if (!getVerbMapping().isEmpty() && mappingCorrespondance==null) {
            throw new RequestMethodCallException(getUrl(),requestMethod);
        }
        return mappingCorrespondance;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    
}