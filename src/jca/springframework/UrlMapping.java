package jca.springframework;

import java.util.HashMap;

import jca.springframework.controller.Mapping;
import jca.springframework.controller.exception.UnhandledUrlException;

public class UrlMapping {
    static public Mapping getMapping(String url , HashMap<String,Mapping> urlMaps) throws UnhandledUrlException{
        Mapping mapping = urlMaps.get(url);
        /// gerer si l'url n'est pas gerer par des controller
        if (mapping == null) {
            throw new UnhandledUrlException(url);
        }
        return mapping;
    }

    static public Mapping getMappingWithFullUrl(String fullurl , HashMap<String,Mapping> urlMaps) throws UnhandledUrlException{
        String url = "";
        try {
            String[] parts = fullurl.split("/");
            url += parts[4];
            for (int i = 5; i < parts.length; i++) {
                url+= parts[i];
            }
        } catch (Exception e) {
            url += "index";
        }
        return getMapping(url, urlMaps);
    }
}
