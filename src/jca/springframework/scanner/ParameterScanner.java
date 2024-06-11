package jca.springframework.scanner;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
public class ParameterScanner {

    public static Parameter[] getParameters(Method method){
        return method.getParameters();
    }
}