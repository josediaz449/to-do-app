package com.example.application.views.todo;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
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
                .setAutoWidth(true).setFlexGrow(0).setSortable(true);
        grid.addColumn(createPriorityComponentRenderer()).setHeader("Priority")
                .setAutoWidth(true).setSortable(true).setComparator(ToDoItem::getPriority);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, this::createDeleteButton)).setHeader("Delete");
        grid.addColumn(ToDoItem::getDateCompleted).setHeader("Date Completed")
                .setAutoWidth(true).setSortable(true);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, this::createCopyButton)).setHeader("Copy over to To-Do").setAutoWidth(true);

    }
    private void createDeleteButton(Button button, ToDoItem todoItem) {
        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e -> this.deleteTodoItem(todoItem));
        button.setIcon(new Icon(VaadinIcon.TRASH));
    }
    private void createCopyButton(Button button, ToDoItem todoItem) {
        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e -> copyItem(todoItem));
        button.setIcon(new Icon(VaadinIcon.COPY));
    }

    private void copyItem(ToDoItem todoItem) {
        Notification notification = createSuccessNotification("Item Copied!");
        notification.open();
        this.toDoItemService.createToDoItem(new ToDoItem(todoItem.getDescription(), todoItem.getPriority()));
    }

    private void createIncompleteButton(Button button, ToDoItem todoItem) {
        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e -> this.selectIncomplete(todoItem));
        button.setIcon(new Icon(VaadinIcon.LIST_UL));
    }

    @Override
    void updateList() {
        List<ToDoItem> toDoItems = toDoItemService.getAllCompletedToDoItems();
        grid.setItems(toDoItems);
        grid.setDetailsVisibleOnClick(true);
        grid.setAllRowsVisible(true);
    }
}
