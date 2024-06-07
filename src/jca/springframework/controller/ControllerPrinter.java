package jca.springframework.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

import jca.springframework.UrlMapping;
import jca.springframework.controller.exception.UnhandledUrlException;

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
    static public void printController(String url ,HashMap<String,Mapping> urlMapping, PrintWriter out) {
        try {
            printController(
                url,
                UrlMapping.getMapping(url, urlMapping),
                out
            );
        } catch (UnhandledUrlException e) {
            /// pas d'exception car url provient de mapping valide
        }
    }
    static public void printController(String url , Mapping mapping , PrintWriter out ){
        out.println("\t-("+url+") "+mapping);
    }

}
