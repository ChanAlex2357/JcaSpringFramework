package jca.springframework.mapping;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jca.springframework.builder.exception.FieldsValidationException;
import jca.springframework.builder.viewbuilder.ViewBuilder;
import jca.springframework.exception.FrameworkException;
import jca.springframework.scanner.RequestScanner;
import jca.springframework.scanner.SessionScanner;
import jca.springframework.scanner.ValidationScanner;
import jca.springframework.session.WebSession;
import jca.springframework.session.WebSessionParser;
import jca.springframework.view.RedirectView;
import jca.springframework.view.View;
public class VerbAction {
    private MappingAnnotation mappingAnnotation;
    private ClassMethode classMethode;
    
    public VerbAction(MappingAnnotation mappingAnnotation, String classControllerName , Method methodeController){
        this.setMappingAnnotation(mappingAnnotation);
        this.setClassMethode(new ClassMethode(classControllerName, methodeController));
    }
    
    public VerbAction(MappingAnnotation mappingAnnotation, ClassMethode classMethode) {
        this.setMappingAnnotation(mappingAnnotation);
        this.setClassMethode(classMethode);
    }

    // FUNCTIONALITIES
    /**
     * Recuperer un instance de la classe controller
     * @param request
     * @return
     */
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
            setSession(controllerInstance, clazz, request);
        } catch (Exception e) {
            /// Exception pour un controller qui n'existe pas
        }
        return controllerInstance;
    }

    /**
     * Instancier la session dans un controller si elle en possede l'attribut
     * @param controllerInstance
     * @param clazz
     * @param request
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public void setSession(Object controllerInstance, Class<?> clazz, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException {
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
    }

    /**
     * Recuperer la valeur de retour d'une method soit l'action d'un controller
     * @param req la requete http servlet
     * @param validationScanner pour traiter les validation de paramtres
     * @return la valeur de retour de l'action
     * @throws IllegalArgumentException
     * @throws FrameworkException
     * @throws InstantiationException
     * @throws IOException
     * @throws ServletException
     * @throws FieldsValidationException
     */
    public Object getMethodResult(HttpServletRequest req,ValidationScanner validationScanner) throws IllegalArgumentException, FrameworkException, InstantiationException, IOException, ServletException, FieldsValidationException{
        Object resultObject = null;
        Object controller =  getControllerInstance(req);
        /// recuperer l'objet methode correspondant avec des parametres null 
        try {
            Class<?>[] parameterTypes = getClassMethode().getMappingParameter().getParameterTypes();
            Method controllerMethod = controller.getClass().getMethod(getClassMethode().getMethodeControllerName(),parameterTypes);
            List<Object> parameterValues = getParameterValues(req,validationScanner);
            validationScanner.thowExceptionIfNeeded();
            resultObject = controllerMethod.invoke(controller,parameterValues.toArray());
        }
        catch (FieldsValidationException fe) {
            RedirectView view = new RedirectView(getMappingAnnotation().getErrorRedirection(), false);
            fe.setErrorAttributes(view);
            resultObject = view;
        }
        catch (NoSuchMethodException | SecurityException e) {
            throw new FrameworkException(e.getMessage(), e);
        }
        catch (IllegalAccessException e) {
            throw new FrameworkException(e.getMessage(), e);
        }
        catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            throw new FrameworkException(cause != null ? cause.getMessage() : e.getMessage(), e);
        }
        return resultObject;
    }
    /**
     * Recuperer les parametres de la requete mapper au parametre de la fonction action
     * @param req la requete
     * @param validationScanner traitement de la validation des parametres
     * @return la liste des parametres necessaires
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws FrameworkException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws SecurityException
     * @throws IOException
     * @throws ServletException
     * @throws FieldsValidationException
     */
    private List<Object> getParameterValues(HttpServletRequest req,ValidationScanner validationScanner) throws IllegalArgumentException, IllegalAccessException, FrameworkException, InstantiationException, InvocationTargetException, SecurityException, IOException, ServletException, FieldsValidationException{
        List<Object> values = new ArrayList<>();
        RequestScanner requestScanner = new RequestScanner();
        Object value = "DEFAULT ";
        for ( Parameter parameter : getClassMethode().getMappingParameter().getParameters()) {
            value =  requestScanner.getParameterValue(parameter, req , validationScanner);
            values.add(value);
        }
        return values;
    }

    /**
     * Recuperer une instance de view selon le type de resultat de la method d'action
     * @param req la requete
     * @return une instance de view selon le 
     * @throws IllegalArgumentException
     * @throws FrameworkException
     * @throws InstantiationException
     * @throws IOException
     * @throws ServletException
     */
    public View getViewResult(HttpServletRequest req)throws IllegalArgumentException, FrameworkException, InstantiationException, IOException, ServletException{
        ValidationScanner validationScanner = new ValidationScanner();
        /// Recuperer l'objet de retour de la methode du controller
        Object methodResult = getMethodResult(req,validationScanner);
        /// Traitement du resultat
        View view =  ViewBuilder.getBuilder(this).buildView(methodResult, this);
        return view;
    }

    @Override
    public String toString() {
        return getClassMethode().getClassControllerName() +" => "+getClassMethode().getMethodeControllerName()+" [ "+getMappingAnnotation()+" ]";
    }
    @Override
    public boolean equals(Object obj) {
        boolean equality = this.getVerb().equals(((VerbAction)obj).getVerb());
        return equality;
    }
    @Override
    public int hashCode() {
        return getVerb().hashCode() *  getMethodeAction().hashCode();
    }
    public String getRoleAccess() {
        return getMappingAnnotation().getRoleAccess();
    }
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
        public String getVerb(){
        return this.getMappingAnnotation().getVerb();
    }
    public String getMethodeAction(){
        return this.getClassMethode().getMethodeControllerName();
    }
    public String getUrl(){
        return this.getMappingAnnotation().getUrl();
    }
}
