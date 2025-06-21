package jca.springframework.utils;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.session.RedirectAttributs;
import jca.springframework.session.WebSession;

public class RequestUtils {
    public static void setAttributs(HttpServletRequest req,Map<String, Object> map){
        setAttributs(req, map, "");
    }
    public static void setAttributs(HttpServletRequest req,Map<String, Object> map,String prefix){
        if (prefix != "" && !prefix.endsWith(".")) {
            prefix += ".";
        }
        for (String attributName : map.keySet()) {
            req.setAttribute(prefix+attributName, map.get(attributName));
        }
    }

    public static void freeSessionCache(HttpServletRequest req, WebSession session) {
        freeRedirections(req, session);
        freeErrors(req, session);
    }
    public static void freeRedirections(HttpServletRequest req, WebSession session) {
        RedirectAttributs redirecAttributs = (RedirectAttributs) session.get(RedirectAttributs.SESSSION_ID);
        if (redirecAttributs == null) {
            return;
        }
        setAttributs(req, redirecAttributs.getRedirectData());
    }

    public static void freeErrors(HttpServletRequest req, WebSession session){

    }
}
