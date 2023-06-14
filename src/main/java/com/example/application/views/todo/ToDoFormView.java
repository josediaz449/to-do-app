package com.example.application.views.todo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class ToDoFormView extends Dialog {
    //Dialog dialog;
    public ToDoFormView() {
        //dialog = new Dialog();

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

        // Center the button within the example
        //getStyle().set("position", "fixed").set("top", "0").set("right", "0")
        //.set("bottom", "0").set("left", "0").set("display", "flex")
                //.set("align-items", "center").set("justify-content", "center");
    }
    private static VerticalLayout createDialogLayout() {

        NumberField priority = new NumberField("Priority");
        TextField description = new TextField("Description");

        VerticalLayout dialogLayout = new VerticalLayout(priority,description);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private static Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Add", e -> dialog.close());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }
}
