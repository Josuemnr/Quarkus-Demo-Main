package org.acme.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.entities.TodoEntity;
import org.acme.infrastructure.mapper.TodoMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TodoRepositoryImpl implements TodoRepository, PanacheRepositoryBase<TodoEntity, UUID> {

    @Override
    @Transactional
    public Todo save(Todo todo) {
        TodoEntity entity = TodoMapper.toEntity(todo);
        persist(entity);
        return TodoMapper.toDomain(entity);
    }

    @Override
    public List<Todo> findAllByUserId(UUID userId) {
        return find("userId", userId)
                .stream()
                .map(TodoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> searchByUserIdAndQuery(UUID userId, String query) {
        String like = "%" + query.toLowerCase() + "%";
        return find("userId = ?1 AND (LOWER(title) LIKE ?2 OR LOWER(description) LIKE ?2)", userId, like)
                .stream()
                .map(TodoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Todo> findTodoById(UUID id) {
        return findByIdOptional(id).map(TodoMapper::toDomain);
    }

    @Override
    @Transactional
    public Todo update(Todo todo) {
        TodoEntity entity = findByIdOptional(todo.getId())
                .map(existing -> {
                    existing.setTitle(todo.getTitle());
                    existing.setDescription(todo.getDescription());
                    existing.setCompleted(todo.isCompleted());
                    return existing;
                })
                .orElseThrow(() -> new NotFoundException("Todo no encontrado: " + todo.getId()));
        return TodoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteTodoById(UUID id) {
        delete("id", id);
    }
}
