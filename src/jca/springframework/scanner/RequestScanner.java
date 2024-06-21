package jca.springframework.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.annotations.Param;
import jca.springframework.exception.FrameworkException;

public class RequestScanner {
    public static Object getParameterValue(Parameter parameter,HttpServletRequest request) throws FrameworkException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, SecurityException{
        Object value = null;
        if (PrimitiveScanner.isPrimitifType(parameter)) {
            value = getPrmitiveParameterValue(parameter, request);
        }
        else {
            value = getObjectParameterValue(parameter, request);
        }
        return value;
    }
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
    public static String getRequestParameter(Parameter parameter,HttpServletRequest request,String prefix , String suffix,String delimiter){
        /// Pattern de paramName
        String paramName = buildParameterName(parameter.getName(),prefix,suffix,delimiter);
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
        return getRequestParameter(parameter, request,null,null,"");
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

    private static Object getObjectParameterValue(Parameter parameter , HttpServletRequest request) throws FrameworkException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, SecurityException{
        // Le resultat attendue
        Object result = null;
        // Recuperer la class type du parametre de la fonction du controller 
        Class<?> parameterType = parameter.getType();
        try {
            // Cree une instance
            Object[] nullist = null;
            Class<?>[] nulliz = null;
            result = parameterType.getConstructor(nulliz).newInstance(nullist);
            // Recuperer la valeur de chaque attribut
            for(Field attribute : parameterType.getDeclaredFields()){
                attribute.setAccessible(true);
                String parameterValue = getRequestParameter(parameter,request,null,attribute.getName(),".");
                attribute.set(result,PrimitiveScanner.parsePrimitive(attribute.getType(), parameterValue));
                attribute.setAccessible(false);
            }
        } catch ( NoSuchMethodException err ) {
            throw new FrameworkException("La class "+parameterType+" doit posseder un constructeur vide\n", err);
        }
        return result; 
    }
}
