package jca.springframework.builder;

import java.io.IOException;
import java.lang.reflect.Parameter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.exception.FrameworkException;
import jca.springframework.scanner.PrimitiveScanner;

public class PrimitiveParameterBuilder extends ParameterBuilder {
    
    public Object getPrmitiveParameterValue(Parameter parameter , HttpServletRequest request) throws FrameworkException, IOException, ServletException{
        // Le resultat attendue
        Object result = null;
        if (PrimitiveScanner.isList(parameter)) {
            result = getRequestParameterValues(parameter, request);
        }
        else {
            String parameterValue = getRequestParameter(parameter, request);
            result = PrimitiveScanner.parsePrimitive(parameter.getType(), parameterValue);
        }
        return result; 
    }
}
