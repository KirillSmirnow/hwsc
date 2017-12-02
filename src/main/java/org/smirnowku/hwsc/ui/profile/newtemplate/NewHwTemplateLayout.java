package org.smirnowku.hwsc.ui.profile.newtemplate;

import com.vaadin.ui.*;
import org.smirnowku.hwsc.ui.dialog.CloseDialogListener;

public class NewHwTemplateLayout extends VerticalLayout {

    private CreateHwTemplateListener createHwTemplateListener;
    private CloseDialogListener closeListener;
    private TextField nameField;
    private TextArea descriptionArea;
    private Button createButton;

    public NewHwTemplateLayout(CreateHwTemplateListener createHwTemplateListener, CloseDialogListener closeListener) {
        this.createHwTemplateListener = createHwTemplateListener;
        this.closeListener = closeListener;
        nameField = new TextField("Name");
        descriptionArea = new TextArea("Description");
        createButton = new Button("Create", clickEvent -> create());

        setSizeFull();
        nameField.setWidth(100, Unit.PERCENTAGE);
        descriptionArea.setWidth(100, Unit.PERCENTAGE);

        addComponents(nameField, descriptionArea, createButton);
        setComponentAlignment(nameField, Alignment.TOP_CENTER);
        setComponentAlignment(descriptionArea, Alignment.MIDDLE_CENTER);
        setComponentAlignment(createButton, Alignment.BOTTOM_CENTER);
    }

    private void create() {
        if (createHwTemplateListener.onCreateHwTemplate(nameField.getValue(), descriptionArea.getValue()))
            closeListener.onClose();
    }
}
