package org.smirnowku.hwsc.ui.assignment;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.smirnowku.hwsc.dto.TaskDto;
import org.smirnowku.hwsc.ui.assignment.actions.SelectTaskListener;
import org.smirnowku.hwsc.ui.assignment.actions.SubmitSolutionListener;
import org.smirnowku.hwsc.ui.dialog.confirm.ConfirmDialog;

@UIScope
@SpringComponent
public class AssignmentSidePanel extends VerticalLayout {

    private SubmitSolutionListener submitSolutionListener;

    private Button submitButton;
    private Label scoreLabel;
    private Grid<TaskDto> tasksGrid;

    public AssignmentSidePanel() {
        submitButton = new Button("Submit Solution", clickEvent -> onSubmit());
        scoreLabel = new Label("Score", ContentMode.HTML);
        tasksGrid = new Grid<>();
        tasksGrid.addColumn(TaskDto::getName).setCaption("Tasks");

        submitButton.setWidth(100, Unit.PERCENTAGE);
        tasksGrid.setWidth(100, Unit.PERCENTAGE);
        tasksGrid.setHeight(600, Unit.PIXELS);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(submitButton, scoreLabel, tasksGrid);
    }

    public void refresh(HomeworkDto homework, Integer score, boolean submittable) {
        tasksGrid.setItems(homework.getTasks());
        submitButton.setVisible(submittable);
        scoreLabel.setVisible(score >= 0);
        scoreLabel.setValue(String.format("<b>Score: %d</b>", score));
    }

    public void setListeners(SelectTaskListener selectTaskListener, SubmitSolutionListener submitSolutionListener) {
        tasksGrid.addSelectionListener(selectTaskListener::onSelect);
        this.submitSolutionListener = submitSolutionListener;
    }

    private void onSubmit() {
        ConfirmDialog dialog = new ConfirmDialog("Submit Solution", "This action cannot be undone!",
                "Submit", submitSolutionListener::onSubmit);
        dialog.showDialog();
    }
}
