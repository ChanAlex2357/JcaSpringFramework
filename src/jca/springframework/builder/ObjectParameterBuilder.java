package jca.springframework.builder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jca.springframework.builder.exception.FieldsValidationException;
import jca.springframework.builder.exception.NoDefaultConstructeurException;
import jca.springframework.exception.FrameworkException;
import jca.springframework.mapping.FileMapping;
import jca.springframework.scanner.PrimitiveScanner;
import jca.springframework.scanner.ValidationScanner;
import jca.springframework.utils.PartUtils;

public class ObjectParameterBuilder extends ParameterBuilder {
    public Object getObjectParameterValue(Parameter parameter , HttpServletRequest request ) throws FrameworkException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, SecurityException, IOException, ServletException, FieldsValidationException{
        ValidationScanner validationScanner = new ValidationScanner();
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
                setObjectParameterValue(result, parameter, attribute, request , validationScanner);
            }
        } catch ( NoSuchMethodException err ) {
            throw new NoDefaultConstructeurException(parameterType,err);
        }
        validationScanner.thowExceptionIfNeeded();
        return result; 
    }

    protected void setObjectParameterValue(Object obj , Parameter parameter , Field attribute , HttpServletRequest request, ValidationScanner validationScanner) throws IllegalArgumentException, IllegalAccessException, FrameworkException, IOException, ServletException {
        attribute.setAccessible(true);
        // Tester si il suit la convention de fichier
        if ( PartUtils.isPartAttribute(attribute) ) {
            setObjectPartValue( obj, parameter, request, attribute ,validationScanner);
        }
        else {
            setObjectPrimitiveValue(obj,parameter, request, attribute, validationScanner);
        }
        attribute.setAccessible(false);
    }

    protected void setObjectPrimitiveValue(Object obj, Parameter parameter , HttpServletRequest request , Field attribute , ValidationScanner validationScanner) throws IllegalArgumentException, IllegalAccessException, FrameworkException, IOException, ServletException{
        String parameterValue = getRequestParameter(parameter,request,null,attribute.getName(),".");
        Object value = PrimitiveScanner.parsePrimitive(attribute.getType(), parameterValue);
        setAttributeValue(obj, attribute, value, validationScanner);
    }

    
    protected void setObjectPartValue(Object obj , Parameter parameter , HttpServletRequest request , Field attribute , ValidationScanner validationScanner) throws IOException, ServletException, IllegalArgumentException, IllegalAccessException, FrameworkException{
        String attributeName = attribute.getName();
        // Recuperer l'objet part correspondant  
        Part part = request.getPart(attributeName);
        if (part == null) {
            return;
        }
        // Instaciaion de l'attribut pour l'objet
        FileMapping fileMapping = new FileMapping(part);
        setAttributeValue(obj,attribute, fileMapping,validationScanner);
    }

    protected void setAttributeValue(Object obj , Field attribute , Object value , ValidationScanner validationScanner) throws IllegalArgumentException, IllegalAccessException{
        validationScanner.checkValidationFieldValue(attribute, value);
        attribute.set(obj, value);
    }
}
