package jca.springframework.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import jca.springframework.annotations.method.Get;
import jca.springframework.annotations.method.Post;
import jca.springframework.annotations.method.RestApi;
import jca.springframework.annotations.method.Url;
import jca.springframework.constants.AnnotationVerb;
import jca.springframework.constants.MethodAnnotation;

public class MethodScanner {
    private static boolean isAnnotedMethod(Method method , Class<? extends Annotation> annotationClass){
        return method.isAnnotationPresent(annotationClass);
    }
    private static Annotation getAnnotedMethod( Method method , Class<? extends Annotation> annotationClass){
        if (isAnnotedMethod(method, annotationClass)) {
            return method.getDeclaredAnnotation(annotationClass);
        }
        return null;
    }
    /*
     * GET Annotation
    */
    public static boolean isGetMethod(Method method){return isAnnotedMethod(method, MethodAnnotation.GET());}
    public static Get getGetAnnotation(Method method){
        Annotation annotation = getAnnotedMethod(method, MethodAnnotation.GET());
        Get getannotation = null;
        if(annotation != null){ getannotation = (Get)annotation ;}
        return getannotation;
    }
    /*
    *  REST API Annotation
    */
    public static boolean isRestApiMethode(Method method){return isAnnotedMethod(method,MethodAnnotation.REST_API());}
    public static RestApi getRestApiAnnotation(Method method){
        Annotation annotation = getAnnotedMethod(method, MethodAnnotation.REST_API());
        RestApi restApi = null;
        if (annotation != null) {restApi = (RestApi) annotation;}
        return restApi;
    }
    /*
     * POST Annotation
     */
    public static boolean isPostAnnotation(Method method){return isAnnotedMethod(method,MethodAnnotation.POST());}
    public static Post  getPostAnnotation(Method menthod){
        Annotation annotation = getAnnotedMethod(menthod, MethodAnnotation.POST());
        Post postannotation = null;
        if (annotation != null) {postannotation = (Post)  annotation; }
        return postannotation;
    }
    /*
     * URL Annotation
     */
    public static boolean isUrlAnnotation(Method method)    {return isAnnotedMethod(method, MethodAnnotation.URL());}
    public static Url getUrlAnnotation(Method method){
        Annotation annotation = getAnnotedMethod(method, MethodAnnotation.URL());
        Url urlannotaion = null;
        if(annotation != null){ urlannotaion = (Url)annotation;}
        return urlannotaion;
    }
    public static String getMethodeVerb(Method method) {
        String verb = null;
        Get getannotation = getGetAnnotation(method);
        Post postannotation = getPostAnnotation(method);
        // Verification pour GET
        verb = checkVerb(verb, getannotation, AnnotationVerb.GET);
        // Verification pour POST
        verb = checkVerb(verb, postannotation, AnnotationVerb.POST);
        // Si il n'y a pas alors on met GET comme verb
        verb = checkVerb(verb, null, AnnotationVerb.GET);
        return verb;
    }
    private static String checkVerb( String initVerb , Annotation annotation , String verbResult){
        if ((initVerb != null) && (annotation == null)) {
            return initVerb;
        }
        return verbResult;
    }
    public static String getMethodeUrl( Method method){
        Url urlannotation = getUrlAnnotation(method);
        if (urlannotation == null) {
            return null;
        }
        String url = urlannotation.path();
        return url;
    }
}