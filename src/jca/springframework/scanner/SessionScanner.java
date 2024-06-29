package jca.springframework.scanner;

import java.lang.reflect.Parameter;
import java.util.List;

import jca.springframework.session.WebSession;

public class SessionScanner {
    public static boolean isSessionParameter(Parameter parameter){
        return isSessionParameter(parameter.getType());
    }
    public static boolean isSessionParameter(Class<?> clazz){
        return clazz.equals(WebSession.class);
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
