package jca.springframework.session;

import jca.springframework.mapping.VerbAction;

public class Authentification {
    public final String AUTH_VARIBLE = "AUTH";
    public final String ROLE_VARIBLE = "ROLE_USER";
    public void auth(WebSession webSession,String role) {
        webSession.add(AUTH_VARIBLE, true);
        webSession.add(ROLE_VARIBLE, role);
    }
    public boolean isAuthorised(WebSession webSession , VerbAction verbAction) {
        boolean authorised = true;
        Object auth = webSession.get(AUTH_VARIBLE);
        String role = (String) webSession.get(ROLE_VARIBLE);
        String verbRole = verbAction.getRoleAccess();

        if (verbRole.equals("public")) {
            authorised = true;
        }
        else if (verbRole.equals("auth") && (auth != null && (boolean)auth)) {
            authorised = true;
        }
        else if((auth != null && (boolean)auth) && (role.equals(verbRole))){
            authorised = true;
        }
        else {
            authorised = false;
        }
        return authorised;
    }
}
