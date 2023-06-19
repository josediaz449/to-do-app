package com.example.application.Service;

import com.example.application.Entity.ToDoItem;

import java.util.List;

public interface ToDoItemService {
    List<ToDoItem> getAllToDoItems();
    ToDoItem getToDoItem(Long toDoItemId);
    ToDoItem createToDoItem(ToDoItem toDoItem);
    ToDoItem updateToDoItem(ToDoItem toDoItem);
    void deleteToDoItem(Long toDoItemId);
}
