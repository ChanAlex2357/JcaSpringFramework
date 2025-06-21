package jca.springframework.view;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RedirectView extends ModelAndView {
    private final boolean isRelative;
    public RedirectView(String url, boolean isRelative) {
        super(url);
        this.isRelative = isRelative;
    }

    public RedirectView(String url) {
        this(url, true);
    }

    @Override 
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAttributs(req);

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
}
