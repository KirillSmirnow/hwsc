package org.smirnowku.hwsc.ui.assignment;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.service.impl.AssignmentService;
import org.smirnowku.hwsc.core.service.impl.HomeworkSolutionService;
import org.smirnowku.hwsc.dto.AssignmentDto;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.smirnowku.hwsc.dto.HomeworkSolutionDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.assignment.actions.SaveSolutionListener;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.ASSIGNMENT)
public class AssignmentView extends VerticalLayout implements View, SaveSolutionListener {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private AssignmentService assignmentService;

    @Resource
    private HomeworkSolutionService homeworkSolutionService;

    @Resource
    private AssignmentTasksLayout assignmentTasksLayout;

    private AssignmentDto assignment;

    private Label nameLabel;
    private Label descriptionLabel;

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
        assignmentTasksLayout.setListeners(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            refresh(Long.valueOf(viewChangeEvent.getParameters()));
        } catch (NumberFormatException e) {
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        }
    }

    @Override
    public boolean onSave(HomeworkSolutionDto homeworkSolution) {
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

    public void refresh(Long id) {
        AssignmentDto assignment;
        try {
            assignment = assignmentService.get(authenticationService.getUsername(), id);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
            return;
        }
        this.assignment = assignment;
        refresh();
    }

    private void refresh() {
        HomeworkDto homework = assignment.getHomework();
        nameLabel.setValue(String.format("<h1>%s</h1>", homework.getName()));
        if (PropertyValidator.isEmpty(homework.getDescription())) {
            descriptionLabel.setVisible(false);
        } else {
            descriptionLabel.setVisible(true);
            descriptionLabel.setValue(String.format("<i>%s</i>", homework.getDescription()));
        }
        assignmentTasksLayout.refresh(homework);
    }
}
