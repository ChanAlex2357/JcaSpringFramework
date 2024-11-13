package jca.springframework.parameterbuilder;

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
        String parameterValue = getRequestParameter(parameter, request);
        // Recuperer la class type du parametre de la fonction du controller 
        Class<?> parameterType = parameter.getType();
        result = PrimitiveScanner.parsePrimitive(parameterType, parameterValue);
        return result; 
    }
}
