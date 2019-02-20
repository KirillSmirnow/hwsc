package hwsc.ui.assignment;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import hwsc.HwscException;
import hwsc.dto.*;
import hwsc.model.Assignment;
import hwsc.model.Check;
import hwsc.service.AssignmentService;
import hwsc.service.CheckService;
import hwsc.service.HomeworkSolutionService;
import hwsc.ui.Views;
import hwsc.ui.assignment.actions.SaveSolutionListener;
import hwsc.ui.assignment.actions.SubmitCheckListener;
import hwsc.ui.assignment.actions.SubmitSolutionListener;
import hwsc.ui.auth.AuthenticationService;
import hwsc.util.PropertyValidator;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.ASSIGNMENT)
public class AssignmentView extends VerticalLayout implements View,
        SaveSolutionListener, SubmitSolutionListener, SubmitCheckListener {

    private final Label nameLabel;
    private final Label descriptionLabel;
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
            homeworkSolutionService.save(authenticationService.getUsername(),
                    homeworkSolution.getId(), homeworkSolution);
        } catch (HwscException e) {
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
        } catch (HwscException e) {
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
        } catch (HwscException e) {
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
        } catch (HwscException e) {
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
        } catch (HwscException e) {
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
