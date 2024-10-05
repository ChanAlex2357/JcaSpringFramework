package jca.springframework.mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.exception.FrameworkException;
import jca.springframework.scanner.RequestScanner;
import jca.springframework.scanner.SessionScanner;
import jca.springframework.session.WebSession;
import jca.springframework.session.WebSessionParser;
import jca.springframework.view.View;
import jca.springframework.view.ViewBuilder;
import jca.springframework.view.exception.InvalidReturnException;

public class Mapping {
    private String classControllerName;
    private String methodeControllerName;
    private MappingParameter mappingParameter;      // Liste des parametres de la methode
    private MappingAnnotation mappingAnnotation;    // Configuration de l'annotation de la methode

    @Override
    public String toString() {
        return getClassControllerName() +" => "+getMethodeControllerName()+" [ "+getMappingAnnotation()+" ]";
    }
    public Mapping(String classControllerName,Method method,MappingAnnotation mappingAnnotation)
    {
        setClassControllerName(classControllerName);
        setMethodeControllerName(method.getName());
        setMappingAnnotation(mappingAnnotation);
        setMappingParameter(new MappingParameter(method));
    }
// GET AND SET
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
    public MappingAnnotation getMappingAnnotation() {
        return mappingAnnotation;
    }
    public void setMappingAnnotation(MappingAnnotation mappingAnnotation) {
        this.mappingAnnotation = mappingAnnotation;
    }
// FUNCTIONALITIES
    public Object getControllerInstance(HttpServletRequest request){
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
            for(Field attribut : clazz.getDeclaredFields()){
                // Tester si l'attribut est une session
                if (SessionScanner.isSessionField(attribut)) {
                    attribut.setAccessible(true);
                    // Instancier une session
                    WebSession session = WebSessionParser.HttpSessionToWebSession(request);
                    attribut.set(controllerInstance, session);
                    attribut.setAccessible(false);
                    break;
                }
            }
        } catch (Exception e) {
            /// Exception pour un controller qui n'existe pas
        }
        return controllerInstance;
    }

    public Object getMethodResult(HttpServletRequest req) throws IllegalArgumentException, FrameworkException ,IllegalArgumentException, FrameworkException, InstantiationException{
        Object resultObject = null;
        Object controller =  getControllerInstance(req);
        /// recuperer l'objet methode correspondant avec des parametres null 
        try {
            Class<?>[] parameterTypes = getMappingParameter().getParameterTypes();
            Method controllerMethod = controller.getClass().getMethod(getMethodeControllerName(),parameterTypes);
            List<Object> parameterValues = getParameterValues(req);
            resultObject = controllerMethod.invoke(controller,parameterValues.toArray());
        }
        catch (NoSuchMethodException | SecurityException e){}
        catch (IllegalAccessException e){} 
        catch (InvocationTargetException e){}
        return resultObject;
    }
    /// Recuperation des donnees necessaires
    private List<Object> getParameterValues(HttpServletRequest req) throws IllegalArgumentException, IllegalAccessException, FrameworkException, InstantiationException, InvocationTargetException, SecurityException{
        List<Object> values = new ArrayList<>(); 
        Object value = "DEFAULT ";
        for ( Parameter parameter : getMappingParameter().getParameters()) {
            value =  RequestScanner.getParameterValue(parameter, req);
            values.add(value);
        }
        return values;
    }

    public View getViewResult(HttpServletRequest req)throws IllegalArgumentException, FrameworkException, InstantiationException{
        /// Recuperer l'objet de retour de la methode du controller
        Object methodResult = getMethodResult(req);
        /// Traitement du resultat

        View view = ViewBuilder.getViewOf(methodResult,getMappingAnnotation());
        /// La vue est null si le resultat ne corespond a aucun format valide
        if ( view == null) {
            throw new InvalidReturnException(this);
        }
        return view;
    }
    
}