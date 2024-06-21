package jca.springframework.scanner;

import java.lang.reflect.Parameter;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.annotations.Param;

public class RequestScanner {
    public static Object getParameterValue(Parameter parameter,HttpServletRequest request){
        Object value = null;
        if (ParamScanner.isPrimitifType(parameter)) {
            value = getPrmitiveParameterValue(parameter, request);
        }
        return value;
    }
    private static Object getPrmitiveParameterValue(Parameter parameter , HttpServletRequest request){
        // Le resultat attendue
        Object result = null;
        String parameterValue = request.getParameter(parameter.getName());
        if (parameterValue == null) {
            try {
                Param param = ParamScanner.getParameterParam(parameter);
                parameterValue = request.getParameter(param.name());
            }
            catch (Exception err) {
                /*
                 * Exception pour un param qui n'est pas implementer
                */
            }
        }
        // Recuperer la class type du parametre de la fonction du controller 
        Class<?> parameterType = parameter.getType();
        if (parameterType.equals(String.class)){
            /// Pas de caste si c'est un string
            result = parameterValue;
        }
        else if (parameterType.equals(int.class)) {
            /// Integer cast
            result = Integer.parseInt(parameterValue);
        }
        else if (parameterType.equals(double.class)) {
            /// Double cast
            result = Double.parseDouble(parameterValue);
        }
        return result; 
    }
}
