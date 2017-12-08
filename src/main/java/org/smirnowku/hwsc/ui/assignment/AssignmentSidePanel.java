package org.smirnowku.hwsc.ui.assignment;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.smirnowku.hwsc.dto.TaskDto;
import org.smirnowku.hwsc.ui.assignment.actions.SelectTaskListener;
import org.smirnowku.hwsc.ui.assignment.actions.SubmitCheckListener;
import org.smirnowku.hwsc.ui.assignment.actions.SubmitSolutionListener;
import org.smirnowku.hwsc.ui.dialog.check.CheckDialog;
import org.smirnowku.hwsc.ui.dialog.confirm.ConfirmDialog;

@UIScope
@SpringComponent
public class AssignmentSidePanel extends VerticalLayout {

    private SubmitSolutionListener submitSolutionListener;
    private SubmitCheckListener submitCheckListener;

    private Label scoreLabel;
    private Button submitSolutionButton;
    private Button submitCheckButton;
    private Grid<TaskDto> tasksGrid;

    public AssignmentSidePanel() {
        scoreLabel = new Label("Score", ContentMode.HTML);
        submitSolutionButton = new Button("Submit Solution", clickEvent -> onSubmitSolution());
        submitCheckButton = new Button("Submit Check", clickEvent -> onSubmitCheck());
        tasksGrid = new Grid<>();
        tasksGrid.addColumn(TaskDto::getName).setCaption("Tasks");

        submitSolutionButton.setWidth(100, Unit.PERCENTAGE);
        submitCheckButton.setWidth(100, Unit.PERCENTAGE);
        tasksGrid.setWidth(100, Unit.PERCENTAGE);
        tasksGrid.setHeight(600, Unit.PIXELS);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(scoreLabel, submitSolutionButton, submitCheckButton, tasksGrid);
    }

    public void refresh(HomeworkDto homework, Integer score, boolean solvable, boolean checkable) {
        scoreLabel.setVisible(score >= 0);
        scoreLabel.setValue(String.format("<b>Score: %d</b>", score));
        submitSolutionButton.setVisible(solvable);
        submitCheckButton.setVisible(checkable);
        tasksGrid.setItems(homework.getTasks());
    }

    public void setListeners(SelectTaskListener selectTaskListener, SubmitSolutionListener submitSolutionListener,
                             SubmitCheckListener submitCheckListener) {
        tasksGrid.addSelectionListener(selectTaskListener::onSelect);
        this.submitSolutionListener = submitSolutionListener;
        this.submitCheckListener = submitCheckListener;
    }

    private void onSubmitSolution() {
        ConfirmDialog dialog = new ConfirmDialog("Submit Solution", "This action cannot be undone!",
                "Submit", submitSolutionListener::onSubmitSolution);
        dialog.showDialog();
    }

    private void onSubmitCheck() {
        CheckDialog dialog = new CheckDialog("Submit Check", submitCheckListener::onSubmitCheck);
        dialog.showDialog();
    }
}
