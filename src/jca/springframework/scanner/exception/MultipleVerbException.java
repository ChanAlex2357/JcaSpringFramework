package jca.springframework.scanner.exception;

import java.lang.reflect.Method;
import jca.springframework.exception.FrameworkException;

public class MultipleVerbException extends FrameworkException {

    private final Method method;

    public MultipleVerbException(Method method) {
        super(null); // Utilisation du code 400 pour une mauvaise configuration
        this.method = method;
    }

    @Override
    public String getMessage() {
        return "La méthode " + method.getName() + " possède plusieurs déclarations de verbes HTTP \"GET\" et \"POST\"";
    }

    public Method getMethod() {
        return method;
    }
}
