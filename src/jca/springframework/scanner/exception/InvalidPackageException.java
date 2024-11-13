package jca.springframework.scanner.exception;

import jca.springframework.exception.FrameworkException;

public class InvalidPackageException extends FrameworkException {

    private final String packageName;

    public InvalidPackageException(String packageName) {
        super(null); // On passe null pour le message car on le génère dans getMessage
        this.packageName = packageName;
    }

    @Override
    public String getMessage() {
        return "Le package !! <" + packageName + "> !! n'existe pas";
    }

    public String getPackageName() {
        return packageName;
    }
}
