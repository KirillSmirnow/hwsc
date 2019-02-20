package hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import hwsc.HwscException;
import hwsc.dto.ClassroomDto;
import hwsc.dto.HomeworkDto;
import hwsc.dto.HomeworkTemplateDto;
import hwsc.dto.TaskTemplateDto;
import hwsc.service.ClassroomService;
import hwsc.service.HomeworkService;
import hwsc.ui.Views;
import hwsc.ui.auth.AuthenticationService;
import hwsc.ui.dialog.assignhw.AssignHomeworkDialog;
import hwsc.ui.dialog.assignhw.AssignListener;
import hwsc.ui.dialog.confirm.ConfirmDialog;
import hwsc.ui.dialog.namedescription.NameDescriptionDialog;
import hwsc.ui.dialog.prompt.PromptDialog;
import hwsc.ui.hwtemplate.actions.AddTaskListener;
import hwsc.ui.hwtemplate.actions.DeleteHwTemplateListener;
import hwsc.ui.hwtemplate.actions.EditHwTemplateListener;
import hwsc.ui.hwtemplate.actions.SelectTaskListener;

import javax.annotation.Resource;
import java.util.List;

@UIScope
@SpringComponent
public class HwTemplateSidePanel extends VerticalLayout implements AssignListener {

    private final Grid<TaskTemplateDto> tasksGrid;
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
        } catch (HwscException e) {
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
