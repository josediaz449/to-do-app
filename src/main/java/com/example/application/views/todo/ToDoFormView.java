package com.example.application.views.todo;

import com.example.application.Model.Priority;
import com.example.application.Model.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

public class ToDoFormView extends Dialog {
    static private NumberField priority;
    static private TextField description;
    @Autowired
    ToDoItemServiceImpl toDoItemService = new ToDoItemServiceImpl();
    public ToDoFormView() {

        this.setHeaderTitle("New to-do item");

        VerticalLayout dialogLayout = createDialogLayout();
        this.add(dialogLayout);

        Button saveButton = createSaveButton(this);
        Button cancelButton = new Button("Cancel", e -> {
            this.close();
        });
        this.getFooter().add(cancelButton);
        this.getFooter().add(saveButton);


        this.open();
    }
    private static VerticalLayout createDialogLayout() {

        priority = new NumberField("Priority");
        description = new TextField("Description");

        VerticalLayout dialogLayout = new VerticalLayout(priority,description);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Add", e -> this.saveItem());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }
    public void saveItem(){
        ToDoItem toDoItem = new ToDoItem(description.getValue(), Priority.LOW);
        //toDoItemService.createToDoItem(toDoItem);
        this.close();
    }
}
