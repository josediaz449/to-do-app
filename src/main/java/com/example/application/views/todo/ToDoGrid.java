package com.example.application.views.todo;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;

import java.util.List;

public abstract class ToDoGrid extends Div {
    ToDoItemServiceImpl toDoItemService;
    Grid<ToDoItem> grid;

    public ToDoGrid(ToDoItemServiceImpl toDoItemService) {
        this.toDoItemService = toDoItemService;
        grid = new Grid<>(ToDoItem.class, false);
    }
    private static final SerializableBiConsumer<Span, ToDoItem> priorityComponentUpdater = (
            span, toDoItem) -> {
        span.getElement().setAttribute("theme", "badge");
        span.setText(String.valueOf(toDoItem.getPriority()));
    };
    protected static ComponentRenderer<Span, ToDoItem> createPriorityComponentRenderer() {
        return new ComponentRenderer<>(Span::new, priorityComponentUpdater);
    }

    protected void selectComplete(ToDoItem toDoItem) {
        toDoItem.setCompleted(true);
        toDoItemService.updateToDoItem(toDoItem);
        updateList();
    }
    protected void selectIncomplete(ToDoItem toDoItem) {
        toDoItem.setCompleted(false);
        toDoItemService.updateToDoItem(toDoItem);
        updateList();
    }
    protected void removeTodoItem(ToDoItem todoItem) {
        toDoItemService.deleteToDoItem(todoItem.getId());
        this.updateList();
    }

    abstract void updateList();
}
