package org.acme.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.usecase.LogoutUseCase;
import org.acme.application.usecase.SyncUserUseCase;
import org.acme.infrastructure.security.AuthContext;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    SyncUserUseCase syncUserUseCase;

    @Inject
    LogoutUseCase logoutUseCase;

    @Inject
    AuthContext authContext;

    public AuthResource(SyncUserUseCase syncUserUseCase,
                        LogoutUseCase logoutUseCase,
                        AuthContext authContext) {
        this.syncUserUseCase = syncUserUseCase;
        this.logoutUseCase = logoutUseCase;
        this.authContext = authContext;
    }

    @POST
    @Path("/sync")
    public Response sync() {
        try {
            var user = syncUserUseCase.execute(
                    authContext.getFirebaseUid(),
                    authContext.getFirebaseEmail()
            );
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() {
        try {
            logoutUseCase.execute();
            return Response.ok("Sesión cerrada correctamente").build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
