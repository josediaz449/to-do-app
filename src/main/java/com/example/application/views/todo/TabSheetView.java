package com.example.application.views.todo;

import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;

public class TabSheetView extends Div {
    ToDoItemServiceImpl toDoItemService;
    ToDoList toDoList;
    DoneList doneList;
    public TabSheetView(ToDoItemServiceImpl toDoItemService) {
        this.toDoItemService =toDoItemService;
        TabSheet tabSheet = new TabSheet();
        Tab toDo = new Tab(new Span("To-Do"));
        Tab completed = new Tab(new Span("Completed"));

        toDoList = new ToDoList(toDoItemService);
        doneList = new DoneList(toDoItemService);

        Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
        plusButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        plusButton.setAriaLabel("Add item");
        plusButton.addClickListener(buttonClickEvent -> showToDoForm());
        plusButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
        tabSheet.add(toDo,new HorizontalLayout(toDoList,plusButton));
        tabSheet.add(completed,doneList);
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);
        add(tabSheet);
    }

    private void showToDoForm() {
        ToDoFormView toDoFormView = new ToDoFormView(toDoItemService);
        add(toDoFormView);
        toDoFormView.addOpenedChangeListener(dialogCloseActionEvent -> toDoList.updateList());
    }
}
