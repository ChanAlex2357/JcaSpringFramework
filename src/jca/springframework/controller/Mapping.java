package jca.springframework.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jca.springframework.view.ModelAndView;
import jca.springframework.view.StringView;
import jca.springframework.view.View;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Mapping {
    String classControllerName;
    String methodeControllerName;
    
    @Override
    public String toString() {
        return getClassControllerName() +" => "+getMethodeControllerName();
    }
    public Mapping(String classControllerName, String methodeControllerName) {
        setClassControllerName(classControllerName);
        setMethodeControllerName(methodeControllerName);
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

    public void execute(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        getViewResult().dispatch(req, resp);
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

    public Object getMethodResult(){
        Object resultObject = null;
        Object controller =  getControllerInstance();
        
        /// recuperer l'objet methode correspondant avec des parametres null 
        try {
            Class<?>[] nullist = null;
            Method controllerMethod = controller.getClass().getMethod(getMethodeControllerName(),nullist);
            Object[] nullish = null;
            resultObject = controllerMethod.invoke(controller, nullish);
        }
        catch (NoSuchMethodException | SecurityException e){}
        catch (IllegalAccessException e){} 
        catch (InvocationTargetException e){}
        return resultObject;
    }

    public View getViewResult(){
        View view = null;
        /// Recuperer l'objet de retour de la methode du controller
        Object methodResult = getMethodResult();
        /// Traitement du resultat
        if (methodResult instanceof ModelAndView){
            view = (View)methodResult;
        }
        else if (methodResult instanceof String) {
            view = new StringView( methodResult.toString() );            
        }
        return view;
    }
}
