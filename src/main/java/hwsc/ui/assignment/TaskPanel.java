package hwsc.ui.assignment;

import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import hwsc.dto.AssignmentDto;
import hwsc.dto.TaskDto;
import hwsc.dto.TaskSolutionDto;
import hwsc.ui.assignment.actions.SaveSolutionListener;
import hwsc.ui.assignment.actions.SelectTaskListener;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;

@UIScope
@SpringComponent
public class TaskPanel extends VerticalLayout implements SelectTaskListener {

    private final Label nameLabel;
    private final Label descriptionLabel;
    @Resource
    private SolutionPanel solutionPanel;
    private AssignmentDto assignment;
    private boolean solvable;

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

    @Override
    public void onSelect(SelectionEvent<TaskDto> selectionEvent) {
        Optional<TaskDto> task = selectionEvent.getFirstSelectedItem();
        setVisible(task.isPresent());
        task.ifPresent(this::refresh);
    }

    private void refresh(TaskDto task) {
        int taskIndex = assignment.getHomework().getTasks().indexOf(task);
        TaskSolutionDto taskSolution = assignment.getHomeworkSolution().getTaskSolutions().get(taskIndex);
        nameLabel.setValue(String.format("<b>%s</b><hr/>", task.getName()));
        descriptionLabel.setValue(task.getDescription());
        solutionPanel.refresh(taskSolution, solvable);
    }
}
