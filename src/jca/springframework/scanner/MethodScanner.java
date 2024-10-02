package jca.springframework.scanner;

import java.lang.reflect.Method;

import jca.springframework.annotations.MappingAnnotation;
import jca.springframework.annotations.method.Get;
import jca.springframework.annotations.method.RestApi;

public class MethodScanner {
    private static final Class<Get> GET_ANNOTATION = Get.class;
    private static final Class<RestApi> REST_API_ANNOTATION = RestApi.class;

    public static boolean isGetMethod(Method method){
        return method.isAnnotationPresent(MethodScanner.GET_ANNOTATION);
    }
    public static Get getGetAnnotation(Method method){
        if (isGetMethod(method)) {
            return method.getDeclaredAnnotation(MethodScanner.GET_ANNOTATION);
        }
        return null;
    }
    // Verifie l'existence de l'annotation RestApi sur une methode de controller
    public static boolean isRestApiMethode(Method method){
        return method.isAnnotationPresent(MethodScanner.REST_API_ANNOTATION);
    }
    // Recuperer l'annotation Api
    public static RestApi getRestApiAnnotation(Method method){
        if (isRestApiMethode(method)) {
            return method.getDeclaredAnnotation(MethodScanner.REST_API_ANNOTATION);
        }
        return null;
    }

    public static MappingAnnotation geMappingAnnotation(Method method){

        Get getAnnotation = getGetAnnotation(method);
        if (getAnnotation != null) {
            return new MappingAnnotation(getAnnotation, getAnnotation.url());
        }

        RestApi restApiAnnotation = getRestApiAnnotation(method);
        if (restApiAnnotation != null) {
            return new MappingAnnotation(restApiAnnotation, restApiAnnotation.url());
        }

        return null;
    }
}