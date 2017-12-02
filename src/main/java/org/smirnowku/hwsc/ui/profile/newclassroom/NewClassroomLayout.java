package org.smirnowku.hwsc.ui.profile.newclassroom;

import com.vaadin.ui.*;
import org.smirnowku.hwsc.ui.dialog.CloseDialogListener;

public class NewClassroomLayout extends VerticalLayout {

    private CreateClassroomListener createClassroomListener;
    private CloseDialogListener closeListener;
    private TextField nameField;
    private TextArea descriptionArea;
    private Button createButton;

    public NewClassroomLayout(CreateClassroomListener createClassroomListener, CloseDialogListener closeListener) {
        this.createClassroomListener = createClassroomListener;
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
        if (createClassroomListener.onCreateClassroom(nameField.getValue(), descriptionArea.getValue()))
            closeListener.onClose();
    }
}
