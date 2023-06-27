package com.example.application.Repository;

import com.example.application.Entity.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem,Long> {
    @Query("SELECT t FROM ToDoItem t WHERE t.completed = false ORDER BY priority DESC" )
    List<ToDoItem> getAllNotCompletedToDoItems();

    @Query("SELECT t FROM ToDoItem t WHERE t.completed = true" )
    List<ToDoItem> getAllCompletedToDoItems();
}
