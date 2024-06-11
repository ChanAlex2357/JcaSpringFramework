package jca.springframework.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.scanner.RequestScanner;
import jca.springframework.view.View;
import jca.springframework.view.ViewBuilder;
import jca.springframework.view.exception.InvalidReturnException;

public class Mapping {
    String classControllerName;
    String methodeControllerName;
    MappingParameter mappingParameter;

    @Override
    public String toString() {
        return getClassControllerName() +" => "+getMethodeControllerName();
    }
    public Mapping(String classControllerName,Method method)
    {
        setClassControllerName(classControllerName);
        setMethodeControllerName(method.getName());
        setMappingParameter(new MappingParameter(method));
    }
    public String getClassControllerName() {
        return classControllerName;
    }
    public void setClassControllerName(String classControllerName) {
        this.classControllerName = classControllerName;
    }
    public String getMethodeControllerName() {
        return methodeControllerName;
    }
    public void setMethodeControllerName(String methodeControllerName) {
        this.methodeControllerName = methodeControllerName;
    }
    public MappingParameter getMappingParameter() {
        return mappingParameter;
    }
    public void setMappingParameter(MappingParameter mappingParameter) {
        this.mappingParameter = mappingParameter;
    }

    public Object getControllerInstance(){
        Object controllerInstance = null;
        try {
            /// Recuperer la class correspondant au nom du controller du mapping
            Class<?> clazz = Class.forName(getClassControllerName());
            /// Recuperer le constructeur vide
            Class<?>[] nullist = null;
            Constructor<?> defaultConstructor = clazz.getConstructor(nullist);
            /// Cree une nouvelle instance avec le constructeur
            Object[] nullish = null;
            controllerInstance = defaultConstructor.newInstance(nullish);
        } catch (Exception e) {
            /// Exception pour un controller qui n'existe pas
        }
        return controllerInstance;
    }

    public Object getMethodResult(HttpServletRequest req){
        Object resultObject = null;
        Object controller =  getControllerInstance();
        /// recuperer l'objet methode correspondant avec des parametres null 
        try {

            Class<?>[] parameterTypes = getMappingParameter().getParameterTypes();
            Method controllerMethod = controller.getClass().getMethod(getMethodeControllerName(),parameterTypes);
            Object[] parameterValues = getParameterValues(req);
            resultObject = controllerMethod.invoke(controller, parameterValues);
        }
        catch (NoSuchMethodException | SecurityException e){}
        catch (IllegalAccessException e){} 
        catch (InvocationTargetException e){}
        return resultObject;
    }
    /// Recuperation des donnees necessaires
    private Object[] getParameterValues(HttpServletRequest req){
        List<Object> values = new ArrayList<>(); 
        String value = null;
        for ( Parameter parameter : getMappingParameter().getParameters()) {
            value =  RequestScanner.getParameterValue(parameter, req);
            values.add(value);
        }
        return values.toArray();
    }

    public View getViewResult(HttpServletRequest req)throws InvalidReturnException{
        /// Recuperer l'objet de retour de la methode du controller
        Object methodResult = getMethodResult(req);
        /// Traitement du resultat
        View view = ViewBuilder.getViewOf(methodResult);
        /// La vue est null si le resultat ne corespond a aucun format valide
        if ( view == null) {
            throw new InvalidReturnException(this);
        }
        return view;
    }
}
