package jca.springframework.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.List;

import jca.springframework.session.WebSession;

public class SessionScanner {
    public static boolean isSessionParameter(Parameter parameter){
        return isSessionClass(parameter.getType());
    }
    public static boolean isSessionClass(Class<?> clazz){
        return clazz.equals(WebSession.class);
    }
    public static boolean isSessionField(Field attribut){
        return isSessionClass(attribut.getType());
    }

    public static WebSession getWebSessionInstance(List<Object> objects){
        WebSession webSession = null;
        for (Object object : objects) {
            if ( object instanceof WebSession ) {
                webSession = (WebSession)object;
                break;
            }
        }
        return webSession;
    }
}
