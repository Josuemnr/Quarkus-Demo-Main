package org.acme.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.CreateTodoDto;
import org.acme.application.dto.UpdateTodoDto;
import org.acme.application.usecase.*;

import java.util.UUID;

@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final CreateTodoUseCase createTodoUseCase;
    private final GetTodosUseCase getTodosUseCase;
    private final UpdateTodoUseCase updateTodoUseCase;
    private final DeleteTodoUseCase deleteTodoUseCase;
    private final CompleteTodoUseCase completeTodoUseCase;

    @Inject
    public TodoResource(CreateTodoUseCase createTodoUseCase,
                        GetTodosUseCase getTodosUseCase,
                        UpdateTodoUseCase updateTodoUseCase,
                        DeleteTodoUseCase deleteTodoUseCase,
                        CompleteTodoUseCase completeTodoUseCase) {
        this.createTodoUseCase = createTodoUseCase;
        this.getTodosUseCase = getTodosUseCase;
        this.updateTodoUseCase = updateTodoUseCase;
        this.deleteTodoUseCase = deleteTodoUseCase;
        this.completeTodoUseCase = completeTodoUseCase;
    }

    @GET
    public Response getTodos(@QueryParam("q") String q) {
        return Response.ok(getTodosUseCase.execute(q)).build();
    }

    @POST
    public Response createTodo(CreateTodoDto todoDto) {
        return Response.status(Response.Status.CREATED)
                .entity(createTodoUseCase.execute(todoDto))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTodo(@PathParam("id") UUID id, UpdateTodoDto todoDto) {
        return Response.ok(updateTodoUseCase.execute(id, todoDto)).build();
    }

    @PATCH
    @Path("/{id}/complete")
    public Response completeTodo(@PathParam("id") UUID id) {
        return Response.ok(completeTodoUseCase.execute(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTodo(@PathParam("id") UUID id) {
        deleteTodoUseCase.execute(id);
        return Response.noContent().build();
    }
}
