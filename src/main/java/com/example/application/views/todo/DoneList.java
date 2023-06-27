package com.example.application.views.todo;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;

import java.util.List;

public class DoneList extends ToDoGrid{
    public DoneList(ToDoItemServiceImpl toDoItemService) {
        super(toDoItemService);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, todoItem) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> this.selectComplete(todoItem));
                    button.setIcon(new Icon(VaadinIcon.LIST));
                })).setHeader("Mark as Incomplete").setAutoWidth(true);
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
        grid.addColumn(createCompletedComponentRenderer()).setHeader("Completed Status")
                .setAutoWidth(true);
        grid.addColumn(ToDoItem::getDateCompleted).setHeader("Date Completed")
                .setAutoWidth(true);
        List<ToDoItem> toDoItems = toDoItemService.getAllCompletedToDoItems();
        grid.setItems(toDoItems);
        grid.setAllRowsVisible(true);
        add(grid);
    }

    @Override
    void updateList() {
        List<ToDoItem> toDoItems = toDoItemService.getAllCompletedToDoItems();
        grid.setItems(toDoItems);
        grid.setAllRowsVisible(true);
    }

    private static final SerializableBiConsumer<Span, ToDoItem> completeComponentUpdater = (
            span, toDoItem) -> {
        boolean isCompleted = "true".equalsIgnoreCase(String.valueOf(toDoItem.isCompleted()));
        String theme = String.format("badge %s",
                isCompleted ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(String.valueOf(toDoItem.isCompleted()));
    };

    private static ComponentRenderer<Span, ToDoItem> createCompletedComponentRenderer() {
        return new ComponentRenderer<>(Span::new, completeComponentUpdater);
    }
}
