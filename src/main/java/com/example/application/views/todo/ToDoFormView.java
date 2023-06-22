package com.example.application.views.todo;

import com.example.application.Entity.Priority;
import com.example.application.Entity.ToDoItem;
import com.example.application.Service.ToDoItemServiceImpl;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ToDoFormView extends Dialog {
    static private ComboBox<Priority> priority;
    static private TextField description;
    ToDoItemServiceImpl toDoItemService;
    public ToDoFormView(ToDoItemServiceImpl toDoItemService) {

        this.setHeaderTitle("New to-do item");
        this.toDoItemService = toDoItemService;
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

        priority = new ComboBox<>();
        priority.setItems(Priority.values());
        priority.setRequired(true);
        description = new TextField("Description");

        VerticalLayout dialogLayout = new VerticalLayout(description,priority);
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
        if(description.isEmpty()||priority.isEmpty()){
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div text = new Div(new Text("Fill in all fields."));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> {
                notification.close();
            });

            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(FlexComponent.Alignment.CENTER);

            notification.add(layout);
            notification.open();
        }
        else {
            ToDoItem toDoItem = new ToDoItem(description.getValue(), priority.getValue());
            toDoItemService.createToDoItem(toDoItem);
            this.close();
        }
    }
}
