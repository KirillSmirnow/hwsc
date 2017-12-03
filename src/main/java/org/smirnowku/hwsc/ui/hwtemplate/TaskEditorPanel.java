package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.dto.TaskTemplateDto;
import org.smirnowku.hwsc.ui.hwtemplate.actions.DeleteTaskListener;
import org.smirnowku.hwsc.ui.hwtemplate.actions.EditTaskListener;
import org.smirnowku.hwsc.ui.hwtemplate.actions.SelectTaskListener;

import java.util.Optional;

@UIScope
@SpringComponent
public class TaskEditorPanel extends VerticalLayout implements SelectTaskListener {

    private EditTaskListener editTaskListener;
    private DeleteTaskListener deleteTaskListener;
    private TaskTemplateDto taskTemplate;

    private TextField nameField;
    private RichTextArea descriptionArea;

    public TaskEditorPanel() {
        nameField = new TextField("Name");
        descriptionArea = new RichTextArea("Description");
        TaskOptionsBar taskOptionsBar = new TaskOptionsBar(this::saveTask, this::deleteTask);

        nameField.setWidth(300, Unit.PIXELS);
        descriptionArea.setWidth(90, Unit.PERCENTAGE);
        descriptionArea.setHeight(600, Unit.PIXELS);

        setEnabled(false);
        addComponents(nameField, descriptionArea, taskOptionsBar);
        setComponentAlignment(nameField, Alignment.TOP_CENTER);
        setComponentAlignment(descriptionArea, Alignment.MIDDLE_CENTER);
        setComponentAlignment(taskOptionsBar, Alignment.BOTTOM_CENTER);
    }

    public void setListeners(EditTaskListener editTaskListener, DeleteTaskListener deleteTaskListener) {
        this.editTaskListener = editTaskListener;
        this.deleteTaskListener = deleteTaskListener;
    }

    public void refresh(TaskTemplateDto taskTemplate) {
        this.taskTemplate = taskTemplate;
        nameField.setValue(taskTemplate.getName());
        descriptionArea.setValue(taskTemplate.getDescription() == null ? "" : taskTemplate.getDescription());
    }

    @Override
    public void onSelect(SelectionEvent<TaskTemplateDto> selectionEvent) {
        Optional<TaskTemplateDto> taskTemplate = selectionEvent.getFirstSelectedItem();
        setEnabled(taskTemplate.isPresent());
        nameField.clear();
        descriptionArea.clear();
        taskTemplate.ifPresent(this::refresh);
    }

    private void saveTask(Button.ClickEvent clickEvent) {
        taskTemplate.setName(nameField.getValue());
        taskTemplate.setDescription(descriptionArea.getValue());
        editTaskListener.onEditTask(taskTemplate);
    }

    private void deleteTask(Button.ClickEvent clickEvent) {
        deleteTaskListener.onDeleteTask(taskTemplate);
    }
}
