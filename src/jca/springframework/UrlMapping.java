package jca.springframework;

import java.util.HashMap;

import jca.springframework.controller.Mapping;

public class UrlMapping {
    static public Mapping getMapping(String url , HashMap<String,Mapping> urlMaps){
        Mapping mapping = urlMaps.get(url);
        return mapping;
    }

    static public Mapping getMappingWithFullUrl(String fullurl , HashMap<String,Mapping> urlMaps){
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
