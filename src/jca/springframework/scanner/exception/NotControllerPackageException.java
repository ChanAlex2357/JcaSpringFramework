package jca.springframework.scanner.exception;

import jca.springframework.exception.FrameworkException;

public class NotControllerPackageException extends FrameworkException {

    private final String packageName;
    private final String scannerLog;

    public NotControllerPackageException(String packageName, String scannerLog) {
        super(null); // On passe null pour le message, et on ajoute un code d'état 404
        this.packageName = packageName;
        this.scannerLog = scannerLog;
    }

    @Override
    public String getMessage() {
        return "Le package <" + packageName + "> ne possède aucun contrôleur.\n" + scannerLog;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getScannerLog() {
        return scannerLog;
    }
}
