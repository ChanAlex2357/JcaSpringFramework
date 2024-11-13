package jca.springframework.parameterbuilder;

import java.io.IOException;
import java.lang.reflect.Parameter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.annotations.parameter.Param;
import jca.springframework.exception.FrameworkException;
import jca.springframework.scanner.ParamScanner;

public class ParameterBuilder {
     public static String buildParameterName(String parameterName,String prefix , String suffix,String delimiter){
        String result = "";
        // Ajout du prefix
        if (prefix != null) {
            result += prefix+delimiter;
        }
        // Le nom du parametre
        result += parameterName;
        // Ajout du suffix
        if (suffix != null) {
            result += delimiter+suffix;
        }
        return result;
    }
    public static String buildParameterName(Parameter parameter)throws FrameworkException{
        return buildParameterName(parameter, null, null,"");
    }
    public static String buildParameterName(Parameter parameter , String prefix, String suffix , String delimiter) throws FrameworkException {
        /// Recuperer la valeur par annotation
        Param param = ParamScanner.getParameterParam(parameter);
        if (param == null) {
            throw new FrameworkException("[ ETU 002434 ] : Un parametre ne contient pas de param", null);
        }
        String paramName = buildParameterName(param.name(), prefix, suffix, delimiter);
        return paramName;
    }
    public static String getRequestParameter(Parameter parameter,HttpServletRequest request,String prefix , String suffix,String delimiter) throws FrameworkException, IOException, ServletException{
        String paramName = buildParameterName(parameter, prefix, suffix, delimiter);
        String parameterValue = request.getParameter(paramName);
        return parameterValue;
    }
    public static String getRequestParameter(Parameter parameter,HttpServletRequest request) throws FrameworkException, IOException, ServletException{
        return getRequestParameter(parameter, request,null,null,"");
    }
}
