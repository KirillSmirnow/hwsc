package org.smirnowku.hwsc.ui.assignment;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.model.Assignment;
import org.smirnowku.hwsc.core.model.Check;
import org.smirnowku.hwsc.core.service.AssignmentService;
import org.smirnowku.hwsc.core.service.CheckService;
import org.smirnowku.hwsc.core.service.HomeworkSolutionService;
import org.smirnowku.hwsc.dto.*;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.assignment.actions.SaveSolutionListener;
import org.smirnowku.hwsc.ui.assignment.actions.SubmitCheckListener;
import org.smirnowku.hwsc.ui.assignment.actions.SubmitSolutionListener;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.ASSIGNMENT)
public class AssignmentView extends VerticalLayout implements View,
        SaveSolutionListener, SubmitSolutionListener, SubmitCheckListener {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private AssignmentService assignmentService;

    @Resource
    private CheckService checkService;

    @Resource
    private HomeworkSolutionService homeworkSolutionService;

    @Resource
    private AssignmentTasksLayout assignmentTasksLayout;

    private CheckDto check;
    private AssignmentDto assignment;

    private final Label nameLabel;
    private final Label descriptionLabel;

    public AssignmentView() {
        nameLabel = new Label("Name", ContentMode.HTML);
        descriptionLabel = new Label("Description", ContentMode.HTML);
    }

    @PostConstruct
    public void init() {
        addComponents(nameLabel, descriptionLabel, assignmentTasksLayout);
        setComponentAlignment(nameLabel, Alignment.TOP_CENTER);
        setComponentAlignment(descriptionLabel, Alignment.TOP_CENTER);
        setComponentAlignment(assignmentTasksLayout, Alignment.MIDDLE_CENTER);
        assignmentTasksLayout.setListeners(this, this, this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            String[] params = viewChangeEvent.getParameters().split("/");
            Long id = Long.valueOf(params[1]);
            if (params[0].equals("check")) refreshByCheck(id);
            else refresh(id);
        } catch (Exception e) {
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        }
    }

    @Override
    public boolean onSave(TaskSolutionDto taskSolution) {
        HomeworkSolutionDto homeworkSolution = assignment.getHomeworkSolution();
        int taskIndex = homeworkSolution.getTaskSolutions().indexOf(taskSolution);
        homeworkSolution.getTaskSolutions().set(taskIndex, taskSolution);
        try {
            homeworkSolutionService.save(authenticationService.getUsername(), homeworkSolution.getId(), homeworkSolution);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            refresh(assignment.getId());
            return false;
        }
        refresh(assignment.getId());
        return true;
    }

    @Override
    public boolean onSubmitSolution() {
        try {
            assignmentService.submit(authenticationService.getUsername(), assignment.getId());
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            refresh(assignment.getId());
            return false;
        }
        UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        return true;
    }

    @Override
    public boolean onSubmitCheck(Integer score) {
        try {
            assignment.setScore(score);
            checkService.submit(authenticationService.getUsername(), check.getId(), assignment);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            return false;
        }
        UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        return true;
    }

    private void refresh(Long id) {
        AssignmentDto assignment;
        try {
            assignment = assignmentService.get(authenticationService.getUsername(), id);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
            return;
        }
        this.assignment = assignment;
        refresh(assignment.getStatus() == Assignment.Status.TODO, false);
    }

    private void refreshByCheck(Long id) {
        CheckDto check;
        try {
            check = checkService.get(authenticationService.getUsername(), id);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
            return;
        }
        this.check = check;
        this.assignment = check.getAssignment();
        refresh(false, check.getStatus() == Check.Status.PENDING);
    }

    private void refresh(boolean solvable, boolean checkable) {
        HomeworkDto homework = assignment.getHomework();
        nameLabel.setValue(String.format("<h1>%s</h1>", homework.getName()));
        if (PropertyValidator.isEmpty(homework.getDescription())) {
            descriptionLabel.setVisible(false);
        } else {
            descriptionLabel.setVisible(true);
            descriptionLabel.setValue(String.format("<i>%s</i>", homework.getDescription()));
        }
        assignmentTasksLayout.refresh(assignment, solvable, checkable);
    }
}