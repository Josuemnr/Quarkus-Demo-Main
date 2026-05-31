package org.acme.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.RegisterUserDto;
import org.acme.application.usecase.GetProfileUseCase;
import org.acme.application.usecase.RegisterUserUseCase;
import org.acme.infrastructure.security.AuthContext;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    RegisterUserUseCase registerUserUseCase;

    @Inject
    GetProfileUseCase getProfileUseCase;

    @Inject
    AuthContext authContext;

    public UserResource(RegisterUserUseCase registerUserUseCase,
                        GetProfileUseCase getProfileUseCase,
                        AuthContext authContext) {
        this.registerUserUseCase = registerUserUseCase;
        this.getProfileUseCase = getProfileUseCase;
        this.authContext = authContext;
    }

    @POST
    public Response registerUser(RegisterUserDto registerUserDto) {
        try {
            return Response.ok(registerUserUseCase.execute(registerUserDto)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/me")
    public Response getProfile() {
        return Response.ok(getProfileUseCase.execute()).build();
    }
}
