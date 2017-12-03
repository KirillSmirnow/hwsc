package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

@UIScope
@SpringComponent
public class TaskOptionsBar extends HorizontalLayout {

    public TaskOptionsBar() {
        Button saveButton = new Button("Save", clickEvent -> save());
        Button deleteButton = new Button("Delete", clickEvent -> delete());
        addComponents(saveButton, deleteButton);
    }

    private void save() {
    }

    private void delete() {
    }
}
