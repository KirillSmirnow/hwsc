package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class ClassroomOptionsBar extends VerticalLayout {

    public ClassroomOptionsBar(Button.ClickListener editListener, Button.ClickListener addMembersListener) {
        Button editButton = new Button("Edit Info", editListener);
        Button addMembersButton = new Button("Add Members", addMembersListener);
        setWidth(300, Unit.PIXELS);
        editButton.setWidth(100, Unit.PERCENTAGE);
        addMembersButton.setWidth(100, Unit.PERCENTAGE);
        addComponents(editButton, addMembersButton);
    }
}
