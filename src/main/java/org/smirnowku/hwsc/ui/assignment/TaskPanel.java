package org.smirnowku.hwsc.ui.assignment;

import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.dto.AssignmentDto;
import org.smirnowku.hwsc.dto.TaskDto;
import org.smirnowku.hwsc.dto.TaskSolutionDto;
import org.smirnowku.hwsc.ui.assignment.actions.SaveSolutionListener;
import org.smirnowku.hwsc.ui.assignment.actions.SelectTaskListener;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;

@UIScope
@SpringComponent
public class TaskPanel extends VerticalLayout implements SelectTaskListener {

    @Resource
    private SolutionPanel solutionPanel;

    private AssignmentDto assignment;
    private boolean solvable;

    private Label nameLabel;
    private Label descriptionLabel;

    public TaskPanel() {
        nameLabel = new Label("", ContentMode.HTML);
        nameLabel.setWidth(90, Unit.PERCENTAGE);
        descriptionLabel = new Label("", ContentMode.HTML);
        descriptionLabel.setWidth(90, Unit.PERCENTAGE);
        setVisible(false);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    }

    @PostConstruct
    public void init() {
        addComponents(nameLabel, descriptionLabel, solutionPanel);
    }

    public void setListeners(SaveSolutionListener saveSolutionListener) {
        solutionPanel.setSaveSolutionListener(saveSolutionListener);
    }

    public void refresh(AssignmentDto assignment, boolean solvable) {
        this.assignment = assignment;
        this.solvable = solvable;
    }

    public void refresh(TaskDto task) {
        int taskIndex = assignment.getHomework().getTasks().indexOf(task);
        TaskSolutionDto taskSolution = assignment.getHomeworkSolution().getTaskSolutions().get(taskIndex);
        nameLabel.setValue(String.format("<b>%s</b><hr/>", task.getName()));
        descriptionLabel.setValue(task.getDescription());
        solutionPanel.refresh(taskSolution, solvable);
    }

    @Override
    public void onSelect(SelectionEvent<TaskDto> selectionEvent) {
        Optional<TaskDto> task = selectionEvent.getFirstSelectedItem();
        setVisible(task.isPresent());
        task.ifPresent(this::refresh);
    }
}
