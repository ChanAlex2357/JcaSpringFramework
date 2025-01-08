package jca.springframework.session;

import jca.springframework.mapping.VerbAction;

public class Authentification {
    public static final String AUTH_VARIBLE = "AUTH";
    public static final String ROLE_VARIBLE = "ROLE_USER";
    private String role;
    public Authentification(String role){
        setRole(role);
    }
    public void auth(WebSession webSession) {
        webSession.add(AUTH_VARIBLE, true);
        webSession.add(ROLE_VARIBLE, this.getRole());
    }
    public static boolean isAuthorised(WebSession webSession , VerbAction verbAction) {
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
    public String getAUTH_VARIBLE() {
        return AUTH_VARIBLE;
    }
    public String getROLE_VARIBLE() {
        return ROLE_VARIBLE;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
