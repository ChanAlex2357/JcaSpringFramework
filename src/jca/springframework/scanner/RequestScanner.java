package jca.springframework.scanner;

import java.lang.reflect.Parameter;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.annotations.Param;

public class RequestScanner {
    public static String getParameterValue(Parameter parameter,HttpServletRequest request){
        String value = request.getParameter(parameter.getName());
        if (value == null) {
            try {
                Param param = ParamScanner.getParameterParam(parameter);
                value = param.name();
            }
            catch (Exception err) {
                /*
                 * Exception pour un param qui n'est pas implementer
                */
            }
        }
        return value;
    }
}
