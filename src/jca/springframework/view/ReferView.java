package jca.springframework.view;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class ReferView extends ModelAndView{

    public ReferView(HttpServletRequest req) {
        super(req.getHeader("Referer"));
    }

    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setViewPathAsControllerUrl();
        super.dispatch(req, resp);
    }
}
