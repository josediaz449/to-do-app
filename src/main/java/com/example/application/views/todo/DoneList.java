package com.example.application.views.todo;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.List;

public class DoneList extends ToDoGrid{
    public DoneList(ToDoItemServiceImpl toDoItemService) {
        super(toDoItemService);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        addColumns();
        updateList();
        add(grid);
    }

    private void addColumns() {
        grid.addColumn(
                new ComponentRenderer<>(Button::new, this::createIncompleteButton)).setHeader("Mark as Incomplete").setAutoWidth(true);
        grid.addColumn(ToDoItem::getDescription).setHeader("Description")
                .setAutoWidth(true);
        grid.addColumn(createPriorityComponentRenderer()).setHeader("Priority")
                .setAutoWidth(true);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, this::createDeleteButton)).setHeader("Delete");
        grid.addColumn(ToDoItem::getDateCompleted).setHeader("Date Completed")
                .setAutoWidth(true);
    }
    private void createDeleteButton(Button button, ToDoItem todoItem) {
        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e -> this.removeTodoItem(todoItem));
        button.setIcon(new Icon(VaadinIcon.TRASH));
    }

    private void createIncompleteButton(Button button, ToDoItem todoItem) {
        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e -> this.selectIncomplete(todoItem));
        button.setIcon(new Icon(VaadinIcon.LIST));
    }

    @Override
    void updateList() {
        List<ToDoItem> toDoItems = toDoItemService.getAllCompletedToDoItems();
        grid.setItems(toDoItems);
        grid.setAllRowsVisible(true);
    }
}
