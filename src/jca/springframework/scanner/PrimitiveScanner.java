package jca.springframework.scanner;

import java.lang.reflect.Parameter;

public class PrimitiveScanner {
    
    public static final Class<?>[] PRIMITIVE_TYPES = new Class[]{
        String.class,
        int.class,
        double.class,
        boolean.class,
        float.class
    };
    public static boolean isPrimitifType(Parameter parameter){
        boolean result = false;
        // Recuperer la class type du parametre de la fonction du controller 
        Class<?> typeOrigin = parameter.getType();
        for(Class<?> primitives : PRIMITIVE_TYPES){
            if (typeOrigin.equals(primitives)) {
                result = true;
                break;
            }
        }
        return result;
    }
    public static Object parsePrimitive(Class<?> typeOrigin ,String value){
        Object result = null;
        /// String cast
        if (typeOrigin.equals(String.class)){
            result = value;
        }
        /// Integer cast
        else if (typeOrigin.equals(int.class)) {
            result = Integer.parseInt(value);
        }
        /// Double cast
        else if (typeOrigin.equals(double.class)) {
            result = Double.parseDouble(value);
        }
        // Boolean case
        else if (typeOrigin.equals(boolean.class)){
            result = Boolean.parseBoolean(value);
        }
        // Float
        else if (typeOrigin.equals(float.class)) {
            result = Float.parseFloat(value);
        }
        
        return result;
    }
}
