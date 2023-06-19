package com.example.application.Controller;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping(path="/todoItem/")
public class ToDoItemController {
    @Autowired
    private ToDoItemService toDoItemService;

    @GetMapping("all")
    public List<ToDoItem> getAllCustomers(){
        return toDoItemService.getAllToDoItems();
    }

    @GetMapping("{id}")
    public ToDoItem getToDoItem(@Param("id") String id){
        return toDoItemService.getToDoItem(Long.valueOf(id));
    }

    @PostMapping
    public ToDoItem createToDoItem(@RequestBody ToDoItem toDoItem){
        return toDoItemService.createToDoItem(toDoItem);
    }

    @PutMapping
    public ToDoItem updateToDoItem(@RequestBody ToDoItem toDoItem){
        return toDoItemService.updateToDoItem(toDoItem);
    }
    @DeleteMapping("{id}")
    public void deleteToDoItem(@PathVariable("id") String id){
        toDoItemService.deleteToDoItem(Long.valueOf(id));
    }

}
