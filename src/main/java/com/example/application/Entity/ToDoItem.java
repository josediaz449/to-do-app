package com.example.application.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "items")
public class ToDoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;
    private Priority priority;
    private boolean completed;
    private Date dateCreated;
    private Date dateCompleted;

    public ToDoItem() {
    }

    public ToDoItem(String description, Priority priority) {
        this.description = description;
        this.priority = priority;
        this.completed=false;
        this.dateCreated=Date.valueOf(LocalDate.now());
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
        if(completed){
            setDateCompleted(Date.valueOf(LocalDate.now()));
        }
        else {
            setDateCompleted(null);
        }
    }
    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public Long getId() {
        return this.id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
