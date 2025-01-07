package jca.springframework.session;

public class Authentication {
    public final String AUTH_VARIBLE = "AUTH";
    public final String ROLE_VARIBLE = "ROLE_USER";
    public void auth(WebSession webSession,String role) {
        webSession.add(AUTH_VARIBLE, true);
        webSession.add(ROLE_VARIBLE, role);
    }
    public boolean isAuthorised(WebSession webSession , Ver)
}
