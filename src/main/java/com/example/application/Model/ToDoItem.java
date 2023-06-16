package com.example.application.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "items")
public class ToDoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Priority priority;
    private boolean completed;
    private LocalDate dateCreated;
    private LocalDate dateCompleted;

    public ToDoItem() {
    }

    public ToDoItem(String description, Priority priority) {
        this.description = description;
        this.priority = priority;
        this.completed=false;
        this.dateCreated=LocalDate.now();
        this.dateCompleted = null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
}
