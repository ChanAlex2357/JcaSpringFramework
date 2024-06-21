package jca.springframework.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MappingParameter {
    Parameter[] parameters=null;
    Class<?>[] parameterTypes = null;
    public MappingParameter(Method method){
        setParameters(method.getParameters());
        setParameterTypes(method.getParameterTypes());
    }
    public Parameter[] getParameters() {
        return parameters;
    }
    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }
    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }
    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
