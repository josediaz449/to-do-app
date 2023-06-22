package com.example.application.views.todo;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.component.button.Button;

import java.util.List;
import java.util.Set;

public class ToDoList extends Div {
    ToDoItemServiceImpl toDoItemService;
    Grid<ToDoItem> grid;
    public ToDoList(ToDoItemServiceImpl toDoItemService) {
        this.toDoItemService = toDoItemService;
        grid = new Grid<>(ToDoItem.class, false);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, todoItem) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_SUCCESS,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> this.selectComplete(todoItem));
                    button.setIcon(new Icon(VaadinIcon.CHECK));
                })).setHeader("Mark as Complete").setAutoWidth(true);
        grid.addColumn(ToDoItem::getDescription).setHeader("Description")
                .setAutoWidth(true);
        grid.addColumn(createPriorityComponentRenderer()).setHeader("Priority")
                .setAutoWidth(true);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, todoItem) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> this.removeTodoItem(todoItem));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Delete");

        List<ToDoItem> toDoItems = toDoItemService.getAllToDoItems();
        grid.setItems(toDoItems);
        grid.setAllRowsVisible(true);
        add(grid);
    }

    private void removeTodoItem(ToDoItem todoItem) {
        toDoItemService.deleteToDoItem(todoItem.getId());
        this.updateList();
    }

    private void selectComplete(ToDoItem toDoItem) {
        toDoItem.setCompleted(true);
        toDoItemService.updateToDoItem(toDoItem);
        updateList();
    }

    public void updateList() {
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