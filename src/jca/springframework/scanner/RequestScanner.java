package jca.springframework.scanner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jca.springframework.annotations.parameter.Param;
import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.FileMapping;
import jca.springframework.session.WebSessionParser;
import jca.springframework.utils.PartUtils;
import jca.springframework.utils.StringUtils;

public class RequestScanner {
    public static Object getParameterValue(Parameter parameter,HttpServletRequest request) throws FrameworkException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, SecurityException, IOException, ServletException{
        Object value = null;
        if (PrimitiveScanner.isPrimitifType(parameter)) {
            value = getPrmitiveParameterValue(parameter, request);
        }
        else if (SessionScanner.isSessionParameter(parameter)) {
            // Cree une webSession a partir de httpServlet
            value = WebSessionParser.HttpSessionToWebSession(request);
        }
        else if (PartUtils.isPartParameter(parameter)) {
            value = getPartParameterValue(parameter,request);
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
        if (parameterValue == null) {
            Part part = PartUtils.getPartValue(request, paramName);
            byte[] bytes = PartUtils.getFileBytes(part);
            return StringUtils.encode(bytes);
        }
        return parameterValue;
    }
    public static String getRequestParameter(Parameter parameter,HttpServletRequest request) throws FrameworkException, IOException, ServletException{
        return getRequestParameter(parameter, request,null,null,"");
    }
    
    private static Object getPrmitiveParameterValue(Parameter parameter , HttpServletRequest request) throws FrameworkException, IOException, ServletException{
        // Le resultat attendue
        Object result = null;
        String parameterValue = getRequestParameter(parameter, request);
        // Recuperer la class type du parametre de la fonction du controller 
        Class<?> parameterType = parameter.getType();
        result = PrimitiveScanner.parsePrimitive(parameterType, parameterValue);
        return result; 
    }
    private static Object getObjectParameterValue(Parameter parameter , HttpServletRequest request) throws FrameworkException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, SecurityException, IOException, ServletException{
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
                setObjectParameterValue(result, parameter, attribute, request);
            }
        } catch ( NoSuchMethodException err ) {
            throw new FrameworkException("La class "+parameterType+" doit posseder un constructeur vide\n", err);
        }
        return result; 
    }
    private static void setObjectParameterValue(Object obj , Parameter parameter , Field attribute , HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, FrameworkException, IOException, ServletException {
        
        attribute.setAccessible(true);
        // Tester si il suit la convention de fichier
        if ( PartUtils.isPartAttribute(attribute) ) {
            setObjectPartValue( obj, parameter, request, attribute );
        }
        else {
            setObjectPrimitiveValue(obj,parameter, request, attribute);
        }
        attribute.setAccessible(false);
    }
    
    private static void setObjectPrimitiveValue(Object obj, Parameter parameter , HttpServletRequest request , Field attribute) throws IllegalArgumentException, IllegalAccessException, FrameworkException, IOException, ServletException{
        String parameterValue = getRequestParameter(parameter,request,null,attribute.getName(),".");
        if (parameterValue == null) {
            return;
        }
        attribute.set(obj,PrimitiveScanner.parsePrimitive(attribute.getType(), parameterValue));
    }
    
    private static void setObjectPartValue(Object obj , Parameter parameter , HttpServletRequest request , Field attribute) throws IOException, ServletException, IllegalArgumentException, IllegalAccessException, FrameworkException{
        String attributeName = attribute.getName();
        // Recuperer l'objet part correspondant  
        Part part = request.getPart(attributeName);
        if (part == null) {
            part = request.getPart(
                getRequestParameter(parameter, request, null,attributeName, ".")
            );
            if (part == null) {
                return;
            }
        }
        // Instaciaion de l'attribut pour l'objet
        FileMapping fileMapping = new FileMapping(part);
        attribute.set(obj, fileMapping);
    }

    private static Object getPartParameterValue(Parameter parameter , HttpServletRequest request) throws IOException, ServletException, FrameworkException {
        String parameterName = buildParameterName(parameter);
        Part part = request.getPart(parameterName);
        FileMapping fileMapping = new FileMapping(part);
        return fileMapping;
        
    }
}
