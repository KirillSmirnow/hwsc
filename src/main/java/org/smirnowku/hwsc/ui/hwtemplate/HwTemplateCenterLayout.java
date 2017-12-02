package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.service.impl.HomeworkTemplateService;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.dto.TaskTemplateDto;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;

@UIScope
@SpringComponent
public class HwTemplateCenterLayout extends HorizontalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private HomeworkTemplateService homeworkTemplateService;

    @Resource
    private TaskTemplateLayout taskTemplateLayout;

    private HomeworkTemplateDto homeworkTemplate;
    private Grid<TaskTemplateDto> tasksGrid;

    public HwTemplateCenterLayout() {
        tasksGrid = new Grid<>();
        tasksGrid.addColumn(TaskTemplateDto::getName).setCaption("Tasks");
        tasksGrid.addSelectionListener(this::openTask);
    }

    @PostConstruct
    public void init() {
        taskTemplateLayout.setEnabled(false);
        setSizeFull();
        addComponents(tasksGrid, taskTemplateLayout);
        setComponentAlignment(tasksGrid, Alignment.MIDDLE_CENTER);
        setComponentAlignment(taskTemplateLayout, Alignment.MIDDLE_CENTER);
        taskTemplateLayout.setTaskEditListener(this::editTask);
        taskTemplateLayout.setTaskDeleteListener(this::deleteTask);
    }

    public void refresh(HomeworkTemplateDto template) {
        this.homeworkTemplate = template;
        tasksGrid.setItems(template.getTaskTemplates());
    }

    private void openTask(SelectionEvent<TaskTemplateDto> selectionEvent) {
        Optional<TaskTemplateDto> taskTemplate = selectionEvent.getFirstSelectedItem();
        taskTemplateLayout.setEnabled(taskTemplate.isPresent());
        taskTemplateLayout.clear();
        taskTemplate.ifPresent(taskTemplateLayout::refresh);
    }

    private void deleteTask(TaskTemplateDto taskTemplate) {
        homeworkTemplate.getTaskTemplates().remove(taskTemplate);
        editHwTemplate();
    }

    private void editTask(TaskTemplateDto taskTemplate) {
        homeworkTemplate.getTaskTemplates().set(homeworkTemplate.getTaskTemplates().indexOf(taskTemplate), taskTemplate);
        editHwTemplate();
    }

    private void editHwTemplate() {
        try {
            homeworkTemplateService.edit(authenticationService.getUsername(), homeworkTemplate.getId(), homeworkTemplate);
            refresh(homeworkTemplate);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
        }
    }
}
