package com.example.application.views.todo;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;

public abstract class ToDoGrid extends Div {
    ToDoItemServiceImpl toDoItemService;
    Grid<ToDoItem> grid;

    public ToDoGrid(ToDoItemServiceImpl toDoItemService) {
        this.toDoItemService = toDoItemService;
        grid = new Grid<>(ToDoItem.class, false);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
    }
    private static final SerializableBiConsumer<Span, ToDoItem> priorityComponentUpdater = (
            span, toDoItem) -> {
        span.getElement().setAttribute("theme", "badge");
        span.setText(String.valueOf(toDoItem.getPriority()));
    };
    protected static ComponentRenderer<Span, ToDoItem> createPriorityComponentRenderer() {
        return new ComponentRenderer<>(Span::new, priorityComponentUpdater);
    }

    static Notification createSuccessNotification(String message) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setText(message);
        notification.setDuration(2000);
        return notification;
    }

    private static Notification createWarningNotification(String message) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        notification.setText(message);
        notification.setDuration(2000);
        return notification;
    }

    protected void selectComplete(ToDoItem toDoItem) {
        toDoItem.setCompleted(true);
        toDoItemService.updateToDoItem(toDoItem);
        Notification notification = createSuccessNotification("Item Completed!");
        notification.open();
        updateList();
    }
    protected void selectIncomplete(ToDoItem toDoItem) {
        toDoItem.setCompleted(false);
        toDoItemService.updateToDoItem(toDoItem);
        Notification notification = createWarningNotification("Item Marked as Incomplete!");
        notification.open();
        updateList();
    }
    protected void deleteTodoItem(ToDoItem todoItem) {
        toDoItemService.deleteToDoItem(todoItem.getId());
        Notification notification = createWarningNotification("Item Deleted!");
        notification.open();
        this.updateList();
    }

    abstract void updateList();
}
