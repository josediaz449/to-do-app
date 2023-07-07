package com.example.application.views.todo;

import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("To Do")
@Route(value = "to-do")
@RouteAlias(value = "")
public class ToDoView extends VerticalLayout {
    ToDoItemServiceImpl toDoItemService;
    public ToDoView(ToDoItemServiceImpl toDoItemService){
        this.toDoItemService = toDoItemService;
        add(createTabSheetView(toDoItemService));
        setAlignItems(Alignment.CENTER);
    }

    private TabSheetView createTabSheetView(ToDoItemServiceImpl toDoItemService) {
        return new TabSheetView(toDoItemService);
    }

}
