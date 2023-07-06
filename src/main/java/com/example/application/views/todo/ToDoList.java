package com.example.application.views.todo;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.component.button.Button;
import java.util.List;

public class ToDoList extends ToDoGrid {
    public ToDoList(ToDoItemServiceImpl toDoItemService) {
        super(toDoItemService);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        addColumns();
        updateList();
        add(grid);
    }

    private void addColumns() {
        grid.addColumn(
                new ComponentRenderer<>(Button::new, this::createCompleteButton)).setHeader("Mark as Complete").setAutoWidth(true);
        grid.addColumn(ToDoItem::getDescription).setHeader("Description")
                .setAutoWidth(true);
        grid.addColumn(createPriorityComponentRenderer()).setHeader("Priority")
                .setAutoWidth(true);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, this::createDeleteButton)).setHeader("Delete");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, this::createEditButton)).setHeader("Edit");
    }

    private void createDeleteButton(Button button, ToDoItem todoItem) {
        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e -> this.removeTodoItem(todoItem));
        button.setIcon(new Icon(VaadinIcon.TRASH));
    }

    private void createCompleteButton(Button button, ToDoItem todoItem) {
        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_SUCCESS,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e -> this.selectComplete(todoItem));
        button.setIcon(new Icon(VaadinIcon.CHECK));
    }
    private void createEditButton(Button button, ToDoItem todoItem) {
        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e -> this.showEditItemForm(todoItem));
        button.setIcon(new Icon(VaadinIcon.EDIT));
    }
    private void showEditItemForm(ToDoItem toDoItem){
        ToDoFormView toDoFormView = new ToDoFormView("Edit to-do item",toDoItem, toDoItemService);
        add(toDoFormView);
        toDoFormView.addOpenedChangeListener(dialogCloseActionEvent -> updateList());
    }

    @Override
    void updateList() {
        List<ToDoItem> toDoItems = toDoItemService.getAllNotCompletedToDoItems();
        grid.setItems(toDoItems);
        grid.setDetailsVisibleOnClick(true);
        grid.setAllRowsVisible(true);
    }
}