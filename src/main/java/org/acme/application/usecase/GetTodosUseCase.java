package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.security.AuthContext;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class GetTodosUseCase {

    private final TodoRepository todoRepository;
    private final AuthContext authContext;

    @Inject
    public GetTodosUseCase(TodoRepository todoRepository, AuthContext authContext) {
        this.todoRepository = todoRepository;
        this.authContext = authContext;
    }

    public List<Todo> execute(String query) {
        UUID userId = authContext.getUser().getId();
        if (query == null || query.isBlank()) {
            return todoRepository.findAllByUserId(userId);
        }
        return todoRepository.searchByUserIdAndQuery(userId, query);
    }
}
