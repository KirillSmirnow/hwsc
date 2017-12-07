package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalSplitPanel;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.ui.hwtemplate.actions.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class HwTemplateTasksLayout extends HorizontalSplitPanel {

    @Resource
    private HwTemplateSidePanel hwTemplateSidePanel;

    @Resource
    private TaskEditorPanel taskEditorPanel;

    @PostConstruct
    public void init() {
        setSplitPosition(25, Unit.PERCENTAGE);
        setMinSplitPosition(20, Unit.PERCENTAGE);
        setMaxSplitPosition(50, Unit.PERCENTAGE);
        addComponents(hwTemplateSidePanel, taskEditorPanel);
    }

    public void refresh(HomeworkTemplateDto template) {
        hwTemplateSidePanel.refresh(template);
    }

    public void setListeners(EditHwTemplateListener editHwTemplateListener, DeleteHwTemplateListener deleteHwTemplateListener,
                             AddTaskListener addTaskListener, EditTaskListener editTaskListener, DeleteTaskListener deleteTaskListener) {
        hwTemplateSidePanel.setListeners(editHwTemplateListener, deleteHwTemplateListener, addTaskListener, taskEditorPanel);
        taskEditorPanel.setListeners(editTaskListener, deleteTaskListener);
    }
}
