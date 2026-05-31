package org.acme.domain.repository;

import org.acme.domain.models.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepository {
    Todo save(Todo todo);
    List<Todo> findAllByUserId(UUID userId);
    List<Todo> searchByUserIdAndQuery(UUID userId, String query);
    Optional<Todo> findTodoById(UUID id);
    Todo update(Todo todo);
    void deleteTodoById(UUID id);
}
