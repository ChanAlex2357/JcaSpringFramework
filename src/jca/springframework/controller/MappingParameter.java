package jca.springframework.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import jca.springframework.scanner.SessionScanner;

public class MappingParameter {
    Parameter[] parameters=null;
    Class<?>[] parameterTypes = null;
    boolean sessionUsage = false;
    public MappingParameter(Method method){
        setParameters(method.getParameters());
        setParameterTypes(method.getParameterTypes());
        checkSessionUsage();
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
    void checkSessionUsage(){
        for (Class<?> class1 : parameterTypes) {
            if (SessionScanner.isSessionClass(class1)) {
                sessionUsage = true;
                break;
            }
        }
    }
}
