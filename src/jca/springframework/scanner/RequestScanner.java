package jca.springframework.scanner;

import java.io.ObjectStreamClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.annotations.Param;
import jca.springframework.exception.FrameworkException;

public class RequestScanner {
    public static Object getParameterValue(Parameter parameter,HttpServletRequest request) throws FrameworkException, IllegalArgumentException, IllegalAccessException{
        Object value = null;
        if (PrimitiveScanner.isPrimitifType(parameter)) {
            value = getPrmitiveParameterValue(parameter, request);
        }
        else {
            value = getObjectParameterValue(parameter, request);
        }
        return value;
    }
    private static String buildParameterName(String parameterName,String prefix , String suffix,String delimiter){
        return prefix+delimiter+parameterName+delimiter+suffix;
    }
    public static String getRequestParameter(Parameter parameter,HttpServletRequest request,String prefix , String suffix,String delimiter){
        /// Pattern de paramName
        String paramName = buildParameterName(delimiter, prefix, parameter.getName(), delimiter);
        /// Recuperer sans annotation
        String parameterValue = request.getParameter(paramName);
        if (parameterValue == null) {
            /// Recuperer la valeur par annotation
            try {
                Param param = ParamScanner.getParameterParam(parameter);
                paramName = buildParameterName(param.name(), prefix, suffix, delimiter);
                parameterValue = request.getParameter(paramName);
            }
            catch (Exception err) {
                /*
                 * Exception pour un param qui n'est pas implementer
                */
            }
        }
        return parameterValue;
    }
    public static String getRequestParameter(Parameter parameter,HttpServletRequest request){
        return getRequestParameter(parameter, request,"","","");
    }
    
    private static Object getPrmitiveParameterValue(Parameter parameter , HttpServletRequest request){
        // Le resultat attendue
        Object result = null;
        String parameterValue = getRequestParameter(parameter, request);
        // Recuperer la class type du parametre de la fonction du controller 
        Class<?> parameterType = parameter.getType();
        result = PrimitiveScanner.parsePrimitive(parameterType, parameterValue);
        return result; 
    }

    private static Object getObjectParameterValue(Parameter parameter , HttpServletRequest request) throws FrameworkException, IllegalArgumentException, IllegalAccessException{
        // Le resultat attendue
        Object result = null;
        // Recuperer la class type du parametre de la fonction du controller 
        Class<?> parameterType = parameter.getType();
        try {
            // Cree une instance
            result = parameterType.getConstructor(null);
            // Recuperer la valeur de chaque attribut
            for(Field attribute : parameterType.getDeclaredFields()){
                String parameterValue = getRequestParameter(parameter,request,"",attribute.getName(),".");
                attribute.set(result,PrimitiveScanner.parsePrimitive(attribute.getClass(), parameterValue));
            }
        } catch ( NoSuchMethodException err ) {
            throw new FrameworkException("La class "+parameterType+" doit posseder un constructeur vide\n", err);
        }
        return result; 
    }
}
