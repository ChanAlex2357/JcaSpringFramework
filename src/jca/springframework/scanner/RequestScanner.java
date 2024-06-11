package jca.springframework.scanner;

import java.lang.reflect.Parameter;

import jakarta.servlet.http.HttpServletRequest;

public class RequestScanner {
    public static String getParameterValue(Parameter parameter,HttpServletRequest request){
        String value = request.getParameter(parameter.getName());

        return value;
    }
}
