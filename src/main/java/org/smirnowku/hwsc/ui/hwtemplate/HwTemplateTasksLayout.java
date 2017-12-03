package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalSplitPanel;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.ui.hwtemplate.actions.AddTaskListener;
import org.smirnowku.hwsc.ui.hwtemplate.actions.AssignHwListener;
import org.smirnowku.hwsc.ui.hwtemplate.actions.DeleteHwTemplateListener;
import org.smirnowku.hwsc.ui.hwtemplate.actions.EditHwTemplateListener;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class HwTemplateTasksLayout extends HorizontalSplitPanel {

    @Resource
    private HwTemplateSidePanel hwTemplateSidePanel;

    @Resource
    private TaskEditorPanel taskEditorPanel;

    private HomeworkTemplateDto homeworkTemplate;

    @PostConstruct
    public void init() {
        setSplitPosition(20, Unit.PERCENTAGE);
        setMinSplitPosition(20, Unit.PERCENTAGE);
        setMaxSplitPosition(50, Unit.PERCENTAGE);
        addComponents(hwTemplateSidePanel, taskEditorPanel);
    }

    public void refresh(HomeworkTemplateDto template) {
        this.homeworkTemplate = template;
        hwTemplateSidePanel.refresh(template);
    }

    public void setListeners(AssignHwListener assignHwListener, EditHwTemplateListener editHwTemplateListener,
                             DeleteHwTemplateListener deleteHwTemplateListener, AddTaskListener addTaskListener) {
        hwTemplateSidePanel.setListeners(assignHwListener, editHwTemplateListener, deleteHwTemplateListener, addTaskListener);
    }

//    private void openTask(SelectionEvent<TaskTemplateDto> selectionEvent) {
//        Optional<TaskTemplateDto> taskTemplate = selectionEvent.getFirstSelectedItem();
//        taskEditorPanel.setEnabled(taskTemplate.isPresent());
//        taskEditorPanel.clear();
//        taskTemplate.ifPresent(taskEditorPanel::refresh);
//    }
//
//    private void deleteTask(TaskTemplateDto taskTemplate) {
//        homeworkTemplate.getTaskTemplates().remove(taskTemplate);
//        editHwTemplate();
//    }
//
//    private void editTask(TaskTemplateDto taskTemplate) {
//        homeworkTemplate.getTaskTemplates().set(homeworkTemplate.getTaskTemplates().indexOf(taskTemplate), taskTemplate);
//        editHwTemplate();
//    }
}
