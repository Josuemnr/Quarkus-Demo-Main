package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import org.acme.application.dto.UpdateTodoDto;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.security.AuthContext;

import java.util.UUID;

@ApplicationScoped
public class UpdateTodoUseCase {

    private final TodoRepository todoRepository;
    private final AuthContext authContext;

    @Inject
    public UpdateTodoUseCase(TodoRepository todoRepository, AuthContext authContext) {
        this.todoRepository = todoRepository;
        this.authContext = authContext;
    }

    public Todo execute(UUID id, UpdateTodoDto dto) {
        Todo existing = todoRepository.findTodoById(id)
                .orElseThrow(() -> new NotFoundException("Todo no encontrado: " + id));

        if (!existing.getUserId().equals(authContext.getUser().getId())) {
            throw new ForbiddenException("No tienes permiso para modificar este todo");
        }

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCompleted(dto.isCompleted());

        return todoRepository.update(existing);
    }
}
