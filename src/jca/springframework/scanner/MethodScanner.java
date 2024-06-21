package jca.springframework.scanner;

import java.lang.reflect.Method;

import jca.springframework.annotations.Get;

public class MethodScanner {

    public static boolean isGetMethod(Method method){
        return method.isAnnotationPresent(Get.class);
    }

    public static Get getGetAnnotation(Method method){
        if (isGetMethod(method)) {
            return method.getDeclaredAnnotation(Get.class);
        }
        return null;
    }
}