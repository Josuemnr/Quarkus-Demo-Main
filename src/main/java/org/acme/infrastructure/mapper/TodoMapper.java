package org.acme.infrastructure.mapper;

import org.acme.domain.models.Todo;
import org.acme.infrastructure.entities.TodoEntity;

public class TodoMapper {

    public static Todo toDomain(TodoEntity entity){
        Todo todo = new Todo();
        todo.setId(entity.getId());
        todo.setUserId(entity.getUserId());
        todo.setTitle(entity.getTitle());
        todo.setDescription(entity.getDescription());
        todo.setCompleted(entity.isCompleted());
        todo.setCreatedAt(entity.getCreatedAt());
        return todo;
    }

    public static TodoEntity toEntity(Todo todo){
        TodoEntity entity = new TodoEntity();
        entity.setId(todo.getId());
        entity.setUserId(todo.getUserId());
        entity.setTitle(todo.getTitle());
        entity.setDescription(todo.getDescription());
        entity.setCompleted(todo.isCompleted());
        entity.setCreatedAt(todo.getCreatedAt());
        return entity;
    }
}
