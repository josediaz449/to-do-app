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
import com.vaadin.flow.data.provider.ItemCountChangeEvent;

public class TabSheetView extends Div {
    ToDoItemServiceImpl toDoItemService;
    ToDoList toDoList;
    DoneList doneList;
    static Span toDoBadge;
    Span completedBadge;
    public TabSheetView(ToDoItemServiceImpl toDoItemService) {
        this.toDoItemService =toDoItemService;

        TabSheet tabSheet = new TabSheet();

        instantiateBadges(toDoItemService);

        Tab toDo = createTab("To-Do",toDoBadge);
        Tab completed = createTab("Completed",completedBadge);

        instantiateLists(toDoItemService);

        addItemCountChangeListenersToLists();

        tabSheet.addSelectedChangeListener(this::updateListOnTabChange);

        Button addButton = createAddButton();

        tabSheet.add(toDo,new HorizontalLayout(toDoList,addButton));
        tabSheet.add(completed,doneList);

        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);
        add(tabSheet);
    }

    private void addItemCountChangeListenersToLists() {
        toDoList.grid.getListDataView().addItemCountChangeListener(this::updateToDoBadge);
        doneList.grid.getListDataView().addItemCountChangeListener(this::updateCompletedBadge);
    }

    private void instantiateLists(ToDoItemServiceImpl toDoItemService) {
        toDoList = new ToDoList(toDoItemService);
        doneList = new DoneList(toDoItemService);
    }

    private void instantiateBadges(ToDoItemServiceImpl toDoItemService) {
        toDoBadge = createBadge(toDoItemService.getAllNotCompletedToDoItems().size());
        completedBadge = createBadge(toDoItemService.getAllCompletedToDoItems().size());
    }

    private void updateToDoBadge(ItemCountChangeEvent<?> countChange) {
        toDoBadge.setText(String.valueOf(countChange.getItemCount()));
        completedBadge.setText(String.valueOf(toDoItemService.getAllCompletedToDoItems().size()));
    }

    private void updateCompletedBadge(ItemCountChangeEvent<?> countChange) {
        completedBadge.setText(String.valueOf(countChange.getItemCount()));
        toDoBadge.setText(String.valueOf(toDoItemService.getAllNotCompletedToDoItems().size()));
    }

    private static Tab createTab(String title, Span badge) {
        Tab tab = new Tab(new Span(title),badge);
        tab.addClassName(title);
        return tab;
    }
    private static Span createBadge(int value) {
        Span badge = new Span(String.valueOf(value));
        badge.getElement().getThemeList().add("badge small contrast");
        badge.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        return badge;
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
