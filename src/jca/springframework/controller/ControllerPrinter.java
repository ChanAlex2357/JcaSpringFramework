package jca.springframework.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

import jca.springframework.UrlMapping;
import jca.springframework.view.StringView;
import jca.springframework.view.View;

public class ControllerPrinter {
    static public void printControllers(String controllerPackage , HashMap<String,Mapping> urlMapping,PrintWriter out){
        out.println("PACKAGE : "+controllerPackage);
        out.println("CONTROLLERS :");
        Set<String> urls = urlMapping.keySet();
        for (String url : urls) {
            printController(
                url,
                urlMapping,
                out
            );
        }
    }
    static public void printController(String url ,HashMap<String,Mapping> urlMapping, PrintWriter out){
        printController(
            url,
            UrlMapping.getMapping(url, urlMapping),
            out
        );
    }
    static public void printController(String url , Mapping mapping , PrintWriter out ){
        if (mapping == null) {
            out.println( urlMappingErrorMessage(url) );
        }
        else {
            out.println("\t-("+url+") "+mapping);
        }
    }

    static public String urlMappingErrorMessage(String url){
        return "\t Aucun mapping ne correspond a l'url :"+url;
    }
    static public View errorMappingView(String url){
        return new StringView( urlMappingErrorMessage(url) );
    }
}
