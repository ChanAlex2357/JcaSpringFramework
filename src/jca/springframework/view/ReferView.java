package jca.springframework.view;

import jakarta.servlet.http.HttpServletRequest;
public class ReferView extends ModelAndView{

    public ReferView(HttpServletRequest req) {
        super(req.getHeader("Referer"));
    }
}
