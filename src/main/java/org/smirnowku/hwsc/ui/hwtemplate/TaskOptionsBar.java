package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.smirnowku.hwsc.dto.TaskTemplateDto;

@UIScope
@SpringComponent
public class TaskOptionsBar extends HorizontalLayout {

    private TaskTemplateDto taskTemplate;
    private TaskEditListener taskEditListener;
    private TaskDeleteListener taskDeleteListener;
    private Button saveButton;
    private Button deleteButton;

    public TaskOptionsBar() {
        saveButton = new Button("Save", clickEvent -> taskEditListener.onTaskEdit(taskTemplate));
        deleteButton = new Button("Delete", clickEvent -> taskDeleteListener.onTaskDelete(taskTemplate));
        addComponents(saveButton, deleteButton);
    }

    public void refresh(TaskTemplateDto taskTemplate) {
        this.taskTemplate = taskTemplate;
    }

    public void setTaskEditListener(TaskEditListener taskEditListener) {
        this.taskEditListener = taskEditListener;
    }

    public void setTaskDeleteListener(TaskDeleteListener taskDeleteListener) {
        this.taskDeleteListener = taskDeleteListener;
    }
}
