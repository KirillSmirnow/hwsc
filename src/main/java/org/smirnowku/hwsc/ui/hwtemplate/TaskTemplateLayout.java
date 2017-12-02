package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.dto.TaskTemplateDto;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class TaskTemplateLayout extends VerticalLayout {

    @Resource
    private TaskOptionsBar taskOptionsBar;

    private TaskEditListener taskEditListener;
    private TextField nameField;
    private RichTextArea descriptionArea;

    public TaskTemplateLayout() {
        nameField = new TextField("Name");
        descriptionArea = new RichTextArea("Description");
        nameField.setWidth(70, Unit.PERCENTAGE);
        descriptionArea.setHeight(500, Unit.PIXELS);
    }

    @PostConstruct
    public void init() {
        addComponents(taskOptionsBar, nameField, descriptionArea);
        setComponentAlignment(taskOptionsBar, Alignment.TOP_CENTER);
        setComponentAlignment(nameField, Alignment.MIDDLE_CENTER);
        setComponentAlignment(descriptionArea, Alignment.MIDDLE_CENTER);
    }

    public void refresh(TaskTemplateDto taskTemplate) {
        nameField.setValue(taskTemplate.getName());
        descriptionArea.setValue(taskTemplate.getDescription());
        taskOptionsBar.refresh(taskTemplate);
    }

    public void clear() {
        nameField.clear();
        descriptionArea.clear();
    }

    public void setTaskEditListener(TaskEditListener taskEditListener) {
        this.taskEditListener = taskEditListener;
        taskOptionsBar.setTaskEditListener(this::editTask);
    }

    public void setTaskDeleteListener(TaskDeleteListener taskDeleteListener) {
        taskOptionsBar.setTaskDeleteListener(taskDeleteListener);
    }

    private void editTask(TaskTemplateDto taskTemplate) {
        taskTemplate.setName(nameField.getValue());
        taskTemplate.setDescription(descriptionArea.getValue());
        taskEditListener.onTaskEdit(taskTemplate);
    }
}
