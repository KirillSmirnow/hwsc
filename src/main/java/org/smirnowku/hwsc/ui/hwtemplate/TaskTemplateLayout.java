package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.model.TaskTemplate;

@UIScope
@SpringComponent
public class TaskTemplateLayout extends VerticalLayout {

    private TextField nameField;
    private RichTextArea descriptionArea;

    public TaskTemplateLayout() {
        nameField = new TextField("Name");
        descriptionArea = new RichTextArea("Description");
        setSizeFull();
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addComponents(nameField, descriptionArea);
        descriptionArea.setWidth(100, Unit.PERCENTAGE);
    }

    public void refresh(TaskTemplate taskTemplate) {
        nameField.setValue(taskTemplate.getName());
        descriptionArea.setValue(taskTemplate.getDescription());
    }
}
