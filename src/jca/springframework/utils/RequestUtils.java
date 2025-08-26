package jca.springframework.utils;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.session.FieldsValidations;
import jca.springframework.session.RedirectAttributs;
import jca.springframework.session.SessionAttributsMapper;
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

    public static void freeSessionMapper(HttpServletRequest req, WebSession session, SessionAttributsMapper target){
        target = (SessionAttributsMapper) session.pop(target.getSESSION_ID());
        if (target == null) {
            return;
        }
        setAttributs(req, target.getAttributs());
    }

    public static void freeRedirections(HttpServletRequest req, WebSession session){
        freeSessionMapper(req, session, new RedirectAttributs());
    }
    public static void freeErrors(HttpServletRequest req, WebSession session){
        freeSessionMapper(req, session, new FieldsValidations());
    }
}
