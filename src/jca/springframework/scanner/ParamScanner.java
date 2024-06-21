package jca.springframework.scanner;

import java.lang.reflect.Parameter;

import jca.springframework.annotations.Param;

public class ParamScanner {
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

    
}
