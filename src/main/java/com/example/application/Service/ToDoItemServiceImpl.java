package com.example.application.Service;

import com.example.application.Entity.ToDoItem;
import com.example.application.Repository.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ToDoItemServiceImpl implements ToDoItemService{
    @Autowired
    ToDoItemRepository toDoItemRepository;
    @Override
    public List<ToDoItem> getAllToDoItems() {
        return toDoItemRepository.findAll();
    }

    @Override
    public List<ToDoItem> getAllNotCompletedToDoItems() {
        return toDoItemRepository.getAllNotCompletedToDoItems();
    }

    @Override
    public List<ToDoItem> getAllCompletedToDoItems() {
        return toDoItemRepository.getAllCompletedToDoItems();
    }

    @Override
    public List<ToDoItem> getAllNotCompletedToDoItemsByDateCreatedDESC() {
        return toDoItemRepository.getAllNotCompletedToDoItemsByDateCreatedDESC();
    }

    @Override
    public List<ToDoItem> getAllNotCompletedToDoItemsByDateCreatedASC() {
        return toDoItemRepository.getAllNotCompletedToDoItemsByDateCreatedASC();
    }

    @Override
    public List<ToDoItem> getAllNotCompletedToDoItemsByPriorityASC() {
        return toDoItemRepository.getAllNotCompletedToDoItemsByPriorityASC();
    }

    @Override
    public ToDoItem getToDoItem(Long toDoItemId) {
        return toDoItemRepository.findById(toDoItemId).get();
    }

    @Override
    public ToDoItem createToDoItem(ToDoItem toDoItem) {
        return toDoItemRepository.save(toDoItem);
    }

    @Override
    public ToDoItem updateToDoItem(ToDoItem toDoItem) {
        return toDoItemRepository.saveAndFlush(toDoItem);
    }

    @Override
    public void deleteToDoItem(Long toDoItemId) {
        toDoItemRepository.deleteById(toDoItemId);
    }
}
