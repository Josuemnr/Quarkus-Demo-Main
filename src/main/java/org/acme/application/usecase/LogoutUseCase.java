package org.acme.application.usecase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.infrastructure.security.AuthContext;

@ApplicationScoped
public class LogoutUseCase {

    private final AuthContext authContext;

    @Inject
    public LogoutUseCase(AuthContext authContext) {
        this.authContext = authContext;
    }

    public void execute() {
        String firebaseUid = authContext.getUser().getFirebaseUuid();
        try {
            FirebaseAuth.getInstance().revokeRefreshTokens(firebaseUid);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error al revocar la sesión: " + e.getMessage());
        }
    }
}
