package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.CreateTodoDto;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.security.AuthContext;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class CreateTodoUseCase {

    private final TodoRepository todoRepository;

    private final AuthContext authContext;

    @Inject
    public CreateTodoUseCase(TodoRepository todoRepository, AuthContext authContext) {
        this.todoRepository = todoRepository;
        this.authContext = authContext;
    }

    public Todo execute(CreateTodoDto todoDto) {
        Todo todo = new Todo();
        todo.setId(UUID.randomUUID());
        todo.setUserId(authContext.getUser().getId());
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(false);
        todo.setCreatedAt(LocalDateTime.now());
        return todoRepository.save(todo);
    }
}
