package jca.springframework.scanner;

import java.lang.reflect.Parameter;

import jca.springframework.annotations.Param;

public class ParamScanner {
    public static final Class<?>[] PRIMITIVE_TYPES = new Class[]{
        String.class,
        int.class,
        double.class
    };
    static public boolean isParameterParam(Parameter  parameter){
        return parameter.isAnnotationPresent(Param.class);
    }
    static public Param getParameterParam(Parameter parameter){
        Param paramResult = null;
        if (isParameterParam(parameter)) {
            paramResult = parameter.getAnnotation(Param.class);
        }
        return paramResult;
    }

    public static boolean isPrimitifType(Parameter parameter){
        boolean result = false;
        // Recuperer la class type du parametre de la fonction du controller 
        Class<?> parameterType = parameter.getType();
        for(Class<?> primitives : PRIMITIVE_TYPES){
            if (parameterType.equals(primitives)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
