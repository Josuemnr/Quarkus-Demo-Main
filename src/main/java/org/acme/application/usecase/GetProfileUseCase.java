package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.models.User;
import org.acme.infrastructure.security.AuthContext;

@ApplicationScoped
public class GetProfileUseCase {

    private final AuthContext authContext;

    @Inject
    public GetProfileUseCase(AuthContext authContext) {
        this.authContext = authContext;
    }

    public User execute() {
        return authContext.getUser();
    }
}
