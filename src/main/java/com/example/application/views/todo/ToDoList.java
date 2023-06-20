package com.example.application.views.todo;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.function.SerializableBiConsumer;

import java.util.List;
import java.util.Set;

public class ToDoList extends Div {
    ToDoItemServiceImpl toDoItemService;
    Grid<ToDoItem> grid;
    public ToDoList(ToDoItemServiceImpl toDoItemService) {
        this.toDoItemService = toDoItemService;
        grid = new Grid<>(ToDoItem.class, false);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(ToDoItem::getDescription).setHeader("Description")
                .setAutoWidth(true);
        grid.addColumn(createPriorityComponentRenderer()).setHeader("Priority")
                .setAutoWidth(true);

        List<ToDoItem> toDoItems = toDoItemService.getAllToDoItems();
        grid.setItems(toDoItems);
        grid.setAllRowsVisible(true);
        grid.addSelectionListener(this::selectComplete);
        add(grid);
    }

    private void selectComplete(SelectionEvent<Grid<ToDoItem>, ToDoItem> select) {
        Set<ToDoItem> selectedItems = select.getAllSelectedItems();
        for (ToDoItem toDoItem:selectedItems) {
            toDoItem.setCompleted(true);
            toDoItemService.updateToDoItem(toDoItem);
        }
        updateList();
    }

    private void updateList() {
        List<ToDoItem> toDoItems = toDoItemService.getAllToDoItems();
        grid.setItems(toDoItems);
        grid.setAllRowsVisible(true);
    }

    private static final SerializableBiConsumer<Span, ToDoItem> priorityComponentUpdater = (
            span, toDoItem) -> {
        boolean isCompleted = "true".equalsIgnoreCase(String.valueOf(toDoItem.isCompleted()));
        String theme = String.format("badge %s",
                isCompleted ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(String.valueOf(toDoItem.isCompleted()));
    };

    private static ComponentRenderer<Span, ToDoItem> createPriorityComponentRenderer() {
        return new ComponentRenderer<>(Span::new, priorityComponentUpdater);
    }
}