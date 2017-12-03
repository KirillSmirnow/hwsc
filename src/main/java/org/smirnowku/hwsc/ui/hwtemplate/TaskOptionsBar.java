package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class TaskOptionsBar extends HorizontalLayout {

    public TaskOptionsBar(Button.ClickListener saveListener, Button.ClickListener deleteListener) {
        Button saveButton = new Button("Save", saveListener);
        Button deleteButton = new Button("Delete", deleteListener);
        addComponents(saveButton, deleteButton);
    }
}
