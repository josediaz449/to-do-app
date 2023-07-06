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

        Tab toDo = createTab("To-Do");
        Tab completed = createTab("Completed");

        toDoList = new ToDoList(toDoItemService);
        doneList = new DoneList(toDoItemService);

        tabSheet.addSelectedChangeListener(this::updateListOnTabChange);

        Button addButton = createAddButton();

        tabSheet.add(toDo,new HorizontalLayout(toDoList,addButton));
        tabSheet.add(completed,doneList);

        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);
        add(tabSheet);
    }

    private static Tab createTab(String title) {
        Tab tab = new Tab(new Span(title));
        tab.addClassName(title);
        return tab;
    }

    private Button createAddButton() {
        Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
        customizeButton(plusButton);
        plusButton.addClickListener(buttonClickEvent -> showToDoForm());
        return plusButton;
    }

    private static void customizeButton(Button plusButton) {
        plusButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        plusButton.setAriaLabel("Add item");
        plusButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
    }

    private void updateListOnTabChange(TabSheet.SelectedChangeEvent listener) {
        if(listener.getSelectedTab().hasClassName("To-Do")){
            toDoList.updateList();
        }
        else {
            doneList.updateList();
        }
    }

    private void showToDoForm() {
        ToDoFormView toDoFormView = new ToDoFormView("New to-do item",toDoItemService);
        add(toDoFormView);
        toDoFormView.addOpenedChangeListener(dialogCloseActionEvent -> toDoList.updateList());
    }
}
