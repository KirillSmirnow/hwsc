package hwsc.ui.assignment;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalSplitPanel;
import hwsc.dto.AssignmentDto;
import hwsc.ui.assignment.actions.SaveSolutionListener;
import hwsc.ui.assignment.actions.SubmitCheckListener;
import hwsc.ui.assignment.actions.SubmitSolutionListener;

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

    public void refresh(AssignmentDto assignment, boolean solvable, boolean checkable) {
        assignmentSidePanel.refresh(assignment.getHomework(), assignment.getScore(), solvable, checkable);
        taskPanel.refresh(assignment, solvable);
    }

    public void setListeners(SaveSolutionListener saveSolutionListener, SubmitSolutionListener submitSolutionListener,
                             SubmitCheckListener submitCheckListener) {
        assignmentSidePanel.setListeners(taskPanel, submitSolutionListener, submitCheckListener);
        taskPanel.setListeners(saveSolutionListener);
    }
}
