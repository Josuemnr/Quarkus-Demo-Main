package org.acme.application.dto;

public class CreateTodoDto {
    private String title;
    private String description;
    public CreateTodoDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public CreateTodoDto(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
