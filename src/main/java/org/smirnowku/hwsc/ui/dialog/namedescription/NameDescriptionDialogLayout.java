package org.smirnowku.hwsc.ui.dialog.namedescription;

import com.vaadin.ui.*;
import org.smirnowku.hwsc.ui.dialog.CloseDialogListener;

public class NameDescriptionDialogLayout extends VerticalLayout {

    private final ActionListener actionListener;
    private final CloseDialogListener closeListener;
    private final TextField nameField;
    private final TextArea descriptionArea;

    public NameDescriptionDialogLayout(String name, String description, String actionButtonCaption,
                                       ActionListener actionListener, CloseDialogListener closeListener) {
        this.actionListener = actionListener;
        this.closeListener = closeListener;
        nameField = new TextField("Name", name == null ? "" : name);
        descriptionArea = new TextArea("Description", description == null ? "" : description);
        Button actionButton = new Button(actionButtonCaption, clickEvent -> action());

        setSizeFull();
        nameField.setWidth(100, Unit.PERCENTAGE);
        descriptionArea.setWidth(100, Unit.PERCENTAGE);

        addComponents(nameField, descriptionArea, actionButton);
        setComponentAlignment(nameField, Alignment.TOP_CENTER);
        setComponentAlignment(descriptionArea, Alignment.MIDDLE_CENTER);
        setComponentAlignment(actionButton, Alignment.BOTTOM_CENTER);
    }

    private void action() {
        if (actionListener.onAction(nameField.getValue(), descriptionArea.getValue()))
            closeListener.onClose();
    }
}
