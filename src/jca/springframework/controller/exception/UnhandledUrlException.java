package jca.springframework.controller.exception;

import jca.springframework.exception.FrameworkException;

public class UnhandledUrlException extends FrameworkException {

    private final String url;

    public UnhandledUrlException(String url) {
        super(404,null); // On passe null pour le message car on le génère dans getMessage
        this.url = url;
    }

    @Override
    public String getMessage() {
        return "\tL'URL que vous avez demandée n'est gérée par aucun contrôleur.\n\tURL : " + url;
    }

    public String getUrl() {
        return url;
    }
}
