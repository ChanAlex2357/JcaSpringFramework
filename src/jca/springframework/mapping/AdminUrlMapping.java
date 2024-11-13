package jca.springframework.mapping;

import java.util.HashMap;

import jca.springframework.controller.exception.DuplicateUrlException;

public class AdminUrlMapping {
    String controllerPackage ;
    HashMap<String,Mapping> urlMapping;
    public AdminUrlMapping(){
        setUrlMapping(new HashMap<String,Mapping>());
    }
    public HashMap<String, Mapping> getUrlMapping() {
        return urlMapping;
    }
    public void setUrlMapping(HashMap<String, Mapping> urlMapping) {
        this.urlMapping = urlMapping;
    }
    public void addMapping(Mapping mapping) throws DuplicateUrlException{
        String url = mapping.getUrl();
        if (urlMapping.get(url) != null) {
                // Exception de methode identique
                throw new DuplicateUrlException(url,urlMapping.get(url),mapping);
        }
        urlMapping.put(url,mapping);
    }
    /**
     * Ajouter une VerbAction dans un mapping si l'url correspond a un mapping deja existante 
     * sinon on cree une nouvelle Mapping avec le VerbAction et son url 
     * @param verbAction L'action qui correspond a un (verb,url) unique 
     * @return Le Mapping qui contient le VerbAction ajoutee
     * @throws DuplicateUrlException si le VerbAction(verb,url) existe deja
     */
    public Mapping addMappingVerb(VerbAction verbAction) throws DuplicateUrlException {
        String url = verbAction.getUrl();
        Mapping mapping = getUrlMapping().get(url);
        if (mapping != null) {
            boolean added = getUrlMapping().get(url).addVerbAction(verbAction);
            if (!added) {
                // Exception de methode identique
            throw new DuplicateUrlException(url,urlMapping.get(url),mapping);
        }
        }
        else {
            mapping = new Mapping(url,verbAction);
            getUrlMapping().put(url, mapping);
        }
        return mapping;
    }
}
