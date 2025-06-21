package jca.springframework.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import jca.springframework.annotations.method.Auth;
import jca.springframework.annotations.method.ErrorMapping;
import jca.springframework.annotations.method.Get;
import jca.springframework.annotations.method.Post;
import jca.springframework.annotations.method.RestApi;
import jca.springframework.annotations.method.Url;
import jca.springframework.constants.AnnotationVerb;
import jca.springframework.constants.MethodAnnotation;
import jca.springframework.scanner.exception.MultipleVerbException;

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
    public static boolean isUrlAnnotation(Method method){return isAnnotedMethod(method, MethodAnnotation.URL());}
    public static Url getUrlAnnotation(Method method){
        Annotation annotation = getAnnotedMethod(method, MethodAnnotation.URL());
        Url urlannotaion = null;
        if(annotation != null){ urlannotaion = (Url)annotation;}
        return urlannotaion;
    }
    /*
     * AUTH Annotation
     */
    public static boolean isAuthAnnotation(Method method){return isAnnotedMethod(method, MethodAnnotation.AUTH());}
    public static Auth getAuthAnnotation(Method method){
        Annotation annotation = getAnnotedMethod(method, MethodAnnotation.AUTH());
        Auth authannotation = null;
        if(annotation != null){ authannotation = (Auth)annotation;}
        return authannotation;
    }

    /*
     * Error Redirection Annotation
     */
    public static boolean isErrorMapping(Method method){return isAnnotedMethod(method, MethodAnnotation.ERROR_MAPPING());}
    public static ErrorMapping getErrorMappingAnnotionation(Method method){
        Annotation annotation = getAnnotedMethod(method, MethodAnnotation.ERROR_MAPPING());
        ErrorMapping errormappingannotation = null;
        if(annotation != null){ errormappingannotation = (ErrorMapping)annotation;}
        return errormappingannotation;
    }

    
    public static String getMethodeVerb(Method method) throws MultipleVerbException {
        Get getannotation = getGetAnnotation(method);
        Post postannotation = getPostAnnotation(method);
        if (getannotation != null && postannotation != null) {
            throw new MultipleVerbException(method);
        } 
        // Verification pour GET
        if (getannotation != null) {
            return AnnotationVerb.GET;
        }
        // Si il n'y a pas alors on met GET comme verb
        if (postannotation != null) {
            return AnnotationVerb.POST;
        }
        return AnnotationVerb.GET;
    }
    
    public static String getMethodeUrl( Method method){
        Url urlannotation = getUrlAnnotation(method);
        if (urlannotation == null) {
            return null;
        }
        String url = urlannotation.path();
        return url;
    }

    public static String getMethodeRoleAccess(Method method){
        String role = "public";

        Auth authannotation = getAuthAnnotation(method);
        if (authannotation != null) {
            role = authannotation.role();
        }

        return role;
    }
    public static String getMethodeErrorMappint(Method method) {
        ErrorMapping errorMapping = getErrorMappingAnnotionation(method);
        if (errorMapping == null) {
            return null;
        }
        return errorMapping.url();
    }
}