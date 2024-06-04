package jca.springframework.scanner.exception;

import jca.springframework.exception.FrameworkException;

public class NotControllerPackageException extends FrameworkException {
    public NotControllerPackageException(String packageName){
        super(errorMessage(packageName),null);
    }
    static private String errorMessage(String packageName){
        return "Le package <"+packageName+"> ne possede aucun controller";
    }
}
