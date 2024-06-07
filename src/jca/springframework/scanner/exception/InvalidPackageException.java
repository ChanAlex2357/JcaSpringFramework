package jca.springframework.scanner.exception;

import jca.springframework.exception.FrameworkException;

public class InvalidPackageException extends FrameworkException {
    public InvalidPackageException(String packageName){
        super(errorMessage(packageName),null);
    }
    static private String errorMessage(String packageName){
        return "Le package !! <"+packageName+"> !! n'existe pas";
    }
}
