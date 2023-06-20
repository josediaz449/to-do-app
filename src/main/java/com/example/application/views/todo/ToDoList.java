package com.example.application.views.todo;

import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableBiConsumer;

import java.util.List;

public class ToDoList extends Div {
    ToDoItemServiceImpl toDoItemService;
    public ToDoList(ToDoItemServiceImpl toDoItemService) {
        this.toDoItemService = toDoItemService;
        Grid<ToDoItem> grid = new Grid<>(ToDoItem.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(ToDoItem::getDescription).setHeader("Description")
                .setAutoWidth(true);
        grid.addColumn(createStatusComponentRenderer()).setHeader("Status")
                .setAutoWidth(true);

        List<ToDoItem> toDoItems = toDoItemService.getAllToDoItems();
        grid.setItems(toDoItems);
        grid.setAllRowsVisible(true);
        add(grid);
    }
    /*
    private static Renderer<ToDoItem> createItemRenderer() {
        return LitRenderer.<ToDoItem>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<vaadin-avatar img=\"${item.pictureUrl}\" name=\"${item.fullName}\" alt=\"User avatar\"></vaadin-avatar>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.fullName} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.email}" + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("pictureUrl", Person::getPictureUrl)
                .withProperty("fullName", Person::getFullName)
                .withProperty("email", Person::getEmail);
    }
*/
    private static final SerializableBiConsumer<Span, ToDoItem> statusComponentUpdater = (
            span, toDoItem) -> {
        boolean isCompleted = "true".equalsIgnoreCase(String.valueOf(toDoItem.isCompleted()));
        String theme = String.format("badge %s",
                isCompleted ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(String.valueOf(toDoItem.isCompleted()));
    };

    private static ComponentRenderer<Span, ToDoItem> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }
}