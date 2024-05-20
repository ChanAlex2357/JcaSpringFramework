package jca.springframework;

import java.util.HashMap;

import jca.springframework.controller.Mapping;

public class UrlMapping {
    static Mapping getMapping(String url , HashMap<String,Mapping> urlMaps){
        return urlMaps.get(url);
    }
}
