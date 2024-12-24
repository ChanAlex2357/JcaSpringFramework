package jca.springframework.mapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.controller.exception.UnhandledUrlException;
import jca.springframework.utils.StringUtils;

public class UrlMapping  {
    static public void showUrlMaps(HttpServletResponse response , HashMap<String,Mapping> urlMaps) throws IOException{
        PrintWriter writer = response.getWriter();
        for (String  url : urlMaps.keySet()) {
            writer.println("- "+url+" : "+urlMaps.get(url));
        }
        writer.flush();
    }
    static public Mapping getMapping(String url , HashMap<String,Mapping> urlMaps) throws UnhandledUrlException{
        Mapping mapping = urlMaps.get(url);
        /// gerer si l'url n'est pas gerer par des controller
        if (mapping == null) {
            throw new UnhandledUrlException(url);
        }
        return mapping;
    }

    static public Mapping getMappingWithFullUrl(String fullurl , HashMap<String,Mapping> urlMaps) throws UnhandledUrlException{
        String url = StringUtils.getControllerUrl(fullurl);
        return getMapping(url, urlMaps);
    }
}
