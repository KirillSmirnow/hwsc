package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.dto.TaskTemplateDto;
import org.smirnowku.hwsc.ui.hwtemplate.actions.EditTaskListener;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class TaskEditorPanel extends VerticalLayout {

    @Resource
    private TaskOptionsBar taskOptionsBar;

    private EditTaskListener editTaskListener;
    private TextField nameField;
    private RichTextArea descriptionArea;

    public TaskEditorPanel() {
        nameField = new TextField("Name");
        descriptionArea = new RichTextArea("Description");

        nameField.setWidth(300, Unit.PIXELS);
        descriptionArea.setWidth(90, Unit.PERCENTAGE);
        descriptionArea.setHeight(600, Unit.PIXELS);
    }

    @PostConstruct
    public void init() {
        addComponents(nameField, descriptionArea, taskOptionsBar);
        setComponentAlignment(nameField, Alignment.TOP_CENTER);
        setComponentAlignment(descriptionArea, Alignment.MIDDLE_CENTER);
        setComponentAlignment(taskOptionsBar, Alignment.BOTTOM_CENTER);
    }

    public void refresh(TaskTemplateDto taskTemplate) {
        nameField.setValue(taskTemplate.getName());
        descriptionArea.setValue(taskTemplate.getDescription());
//        taskOptionsBar.refresh(taskTemplate);
    }

    public void clear() {
        nameField.clear();
        descriptionArea.clear();
    }

//    public void setEditTaskListener(EditTaskListener editTaskListener) {
//        this.editTaskListener = editTaskListener;
//        taskOptionsBar.setEditTaskListener(this::editTask);
//    }
//
//    public void setDeleteTaskListener(DeleteTaskListener taskDeleteListener) {
//        taskOptionsBar.setDeleteTaskListener(taskDeleteListener);
//    }
//
//    private void editTask(TaskTemplateDto taskTemplate) {
//        taskTemplate.setName(nameField.getValue());
//        taskTemplate.setDescription(descriptionArea.getValue());
//        editTaskListener.onEditTask(taskTemplate);
//    }
}
