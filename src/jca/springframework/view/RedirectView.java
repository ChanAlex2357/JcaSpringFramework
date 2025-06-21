package jca.springframework.view;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jca.springframework.session.RedirectAttributs;
import jca.springframework.session.WebSession;
import jca.springframework.session.WebSessionParser;

public class RedirectView extends ModelAndView {
    private final boolean isRelative;
    public RedirectAttributs redirectData = new RedirectAttributs();
    public RedirectView(String url, boolean isRelative) {
        super(url);
        this.isRelative = isRelative;
    }

    public RedirectView(String url) {
        this(url, true);
    }

    public void addRedirectAttribut(String name, Object value){
        getRedirectData().add(name,value);
    }

    public void addAllRedirectAttribut(Map<String,Object> data){
        getRedirectData().addAll(data);
    }

    @Override 
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAttributs(req);

        WebSession session = WebSessionParser.HttpSessionToWebSession(req);
        session.add(RedirectAttributs.SESSSION_ID, getRedirectData());

        String redirectUrl = getViewPath();
        if (isRelative) {
            String prefix = "/";
            if(redirectUrl.startsWith(prefix)){
                prefix = "";
            }
            redirectUrl = req.getContextPath() +prefix+ redirectUrl;
        }
        resp.sendRedirect(redirectUrl);
    }

    public boolean isRelative() {
        return isRelative;
    }

    public RedirectAttributs getRedirectData() {
        return redirectData;
    }

    public void setRedirectData(RedirectAttributs redirectData) {
        this.redirectData = redirectData;
    }

}
