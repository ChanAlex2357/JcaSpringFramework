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

public class MappingClassMethode {
    private MappingAnnotation mappingAnnotation;
    private ClassMethode classMethode;
    
    public ClassMethode getClassMethode() {
        return classMethode;
    }
    public void setClassMethode(ClassMethode classMethode) {
        this.classMethode = classMethode;
    }
    public MappingAnnotation getMappingAnnotation() {
        return mappingAnnotation;
    }
    public void setMappingAnnotation(MappingAnnotation mappingAnnotation) {
        this.mappingAnnotation = mappingAnnotation;
    }
    public MappingClassMethode(MappingAnnotation mappingAnnotation, String classControllerName , Method methodeController){
        this.setMappingAnnotation(mappingAnnotation);
        this.setClassMethode(new ClassMethode(classControllerName, methodeController));
    }
    
    public MappingClassMethode(MappingAnnotation mappingAnnotation, ClassMethode classMethode) {
        this.setMappingAnnotation(mappingAnnotation);
        this.setClassMethode(classMethode);
    }
    public String getVerb(){
        return this.getMappingAnnotation().getVerb();
    }
    public String getMethodeAction(){
        return this.getClassMethode().getMethodeControllerName();
    }
    // FUNCTIONALITIES
    public Object getControllerInstance(HttpServletRequest request){
        Object controllerInstance = null;
        try {
            /// Recuperer la class correspondant au nom du controller du mapping
            Class<?> clazz = Class.forName(getClassMethode().getClassControllerName());
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
            Class<?>[] parameterTypes = getClassMethode().getMappingParameter().getParameterTypes();
            Method controllerMethod = controller.getClass().getMethod(getClassMethode().getMethodeControllerName(),parameterTypes);
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
        for ( Parameter parameter : getClassMethode().getMappingParameter().getParameters()) {
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
    @Override
    public String toString() {
        return getClassMethode().getClassControllerName() +" => "+getClassMethode().getMethodeControllerName()+" [ "+getMappingAnnotation()+" ]";
    }
    @Override
    public boolean equals(Object obj) {
        boolean equality = this.getVerb().equals(((MappingClassMethode)obj).getVerb());
        return equality;
    }
    @Override
    public int hashCode() {
        return getVerb().hashCode() *  getMethodeAction().hashCode();
    }
    
}
