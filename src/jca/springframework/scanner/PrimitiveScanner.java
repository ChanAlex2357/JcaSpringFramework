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
            try{result = Integer.parseInt(value);}
            catch (Exception e){result = 0;}
        }
        /// Double cast
        else if (typeOrigin.equals(double.class)) {
            try{result = Double.parseDouble(value);}
            catch (Exception e){result = 0;}
        }
        // Boolean case
        else if (typeOrigin.equals(boolean.class)){
            result = Boolean.parseBoolean(value);
        }
        // Float
        else if (typeOrigin.equals(float.class)) {
            try { result = Float.parseFloat(value);}
            catch (Exception e) {result =0;}
        }
        
        return result;
    }
}
