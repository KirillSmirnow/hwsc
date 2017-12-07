package org.smirnowku.hwsc.ui.assignment;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalSplitPanel;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.smirnowku.hwsc.ui.assignment.actions.SaveSolutionListener;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class AssignmentTasksLayout extends HorizontalSplitPanel {

    @Resource
    private AssignmentSidePanel assignmentSidePanel;

    @Resource
    private TaskPanel taskPanel;

    @PostConstruct
    public void init() {
        setSplitPosition(25, Unit.PERCENTAGE);
        setMinSplitPosition(20, Unit.PERCENTAGE);
        setMaxSplitPosition(50, Unit.PERCENTAGE);
        addComponents(assignmentSidePanel, taskPanel);
    }

    public void refresh(HomeworkDto homework) {
        assignmentSidePanel.refresh(homework);
    }

    public void setListeners(SaveSolutionListener saveSolutionListener) {
        assignmentSidePanel.setListeners(taskPanel);
        taskPanel.setListeners(saveSolutionListener);
    }
}
