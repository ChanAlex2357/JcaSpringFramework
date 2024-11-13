package jca.springframework.parameterbuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.FileMapping;
import jca.springframework.scanner.PrimitiveScanner;
import jca.springframework.utils.PartUtils;

public class ObjectParameterBuilder extends ParameterBuilder {
    public Object getObjectParameterValue(Parameter parameter , HttpServletRequest request) throws FrameworkException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, SecurityException, IOException, ServletException{
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

    protected void setObjectParameterValue(Object obj , Parameter parameter , Field attribute , HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, FrameworkException, IOException, ServletException {
        
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
            return;
        }
        // Instaciaion de l'attribut pour l'objet
        FileMapping fileMapping = new FileMapping(part);
        attribute.set(obj, fileMapping);
    }
}
