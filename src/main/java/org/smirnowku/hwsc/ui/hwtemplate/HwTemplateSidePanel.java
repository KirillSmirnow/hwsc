package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.service.ClassroomService;
import org.smirnowku.hwsc.core.service.HomeworkService;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.dto.TaskTemplateDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;
import org.smirnowku.hwsc.ui.dialog.assignhw.AssignHomeworkDialog;
import org.smirnowku.hwsc.ui.dialog.assignhw.AssignListener;
import org.smirnowku.hwsc.ui.dialog.confirm.ConfirmDialog;
import org.smirnowku.hwsc.ui.dialog.namedescription.NameDescriptionDialog;
import org.smirnowku.hwsc.ui.dialog.prompt.PromptDialog;
import org.smirnowku.hwsc.ui.hwtemplate.actions.AddTaskListener;
import org.smirnowku.hwsc.ui.hwtemplate.actions.DeleteHwTemplateListener;
import org.smirnowku.hwsc.ui.hwtemplate.actions.EditHwTemplateListener;
import org.smirnowku.hwsc.ui.hwtemplate.actions.SelectTaskListener;

import javax.annotation.Resource;
import java.util.List;

@UIScope
@SpringComponent
public class HwTemplateSidePanel extends VerticalLayout implements AssignListener {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private HomeworkService homeworkService;

    @Resource
    private ClassroomService classroomService;

    private EditHwTemplateListener editHwTemplateListener;
    private DeleteHwTemplateListener deleteHwTemplateListener;
    private AddTaskListener addTaskListener;

    private HomeworkTemplateDto homeworkTemplate;

    private final Grid<TaskTemplateDto> tasksGrid;

    public HwTemplateSidePanel() {
        Button assignButton = new Button("Assign HW", clickEvent -> assignHw());
        Button editButton = new Button("Edit Template Info", clickEvent -> editInfo());
        Button deleteButton = new Button("Delete Template", clickEvent -> deleteTemplate());
        Button newTaskButton = new Button("New Task", clickEvent -> addTask());
        tasksGrid = new Grid<>();
        tasksGrid.addColumn(TaskTemplateDto::getName).setCaption("Tasks");

        assignButton.setWidth(100, Unit.PERCENTAGE);
        editButton.setWidth(100, Unit.PERCENTAGE);
        deleteButton.setWidth(100, Unit.PERCENTAGE);
        newTaskButton.setWidth(100, Unit.PERCENTAGE);
        tasksGrid.setWidth(100, Unit.PERCENTAGE);
        tasksGrid.setHeight(600, Unit.PIXELS);

        addComponents(assignButton, editButton, deleteButton, newTaskButton, tasksGrid);
    }

    public void refresh(HomeworkTemplateDto template) {
        this.homeworkTemplate = template;
        tasksGrid.setItems(template.getTaskTemplates());
    }

    public void setListeners(EditHwTemplateListener editHwTemplateListener, DeleteHwTemplateListener deleteHwTemplateListener,
                             AddTaskListener addTaskListener, SelectTaskListener selectTaskListener) {
        this.editHwTemplateListener = editHwTemplateListener;
        this.deleteHwTemplateListener = deleteHwTemplateListener;
        this.addTaskListener = addTaskListener;
        tasksGrid.addSelectionListener(selectTaskListener::onSelect);
    }

    @Override
    public boolean onAssign(Long classroomId, HomeworkDto homework) {
        try {
            homeworkService.assign(authenticationService.getUsername(), homeworkTemplate.getId(), classroomId, homework);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            return false;
        }
        UI.getCurrent().getNavigator().navigateTo(Views.classroom(classroomId));
        return true;
    }

    private void assignHw() {
        List<ClassroomDto> classrooms = classroomService.getClassroomsAsTeacher(authenticationService.getUsername());
        AssignHomeworkDialog dialog = new AssignHomeworkDialog("Assign Homework", classrooms, this);
        dialog.showDialog();
    }

    private void editInfo() {
        NameDescriptionDialog dialog = new NameDescriptionDialog("Edit Info", homeworkTemplate.getName(),
                homeworkTemplate.getDescription(), "Save", (name, description) -> {
            homeworkTemplate.setName(name);
            homeworkTemplate.setDescription(description);
            return editHwTemplateListener.onEditHwTemplate(homeworkTemplate);
        });
        dialog.showDialog();
    }

    private void deleteTemplate() {
        ConfirmDialog dialog = new ConfirmDialog("Delete HW Template", "Are you sure you want to delete this template?",
                "Delete", deleteHwTemplateListener::onDeleteHwTemplate);
        dialog.showDialog();
    }

    private void addTask() {
        PromptDialog dialog = new PromptDialog("Add Task", "Name", "Create",
                value -> addTaskListener.onAddTask(new TaskTemplateDto(value)));
        dialog.showDialog();
    }
}
