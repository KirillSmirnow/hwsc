package org.smirnowku.hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.service.impl.AssignmentService;
import org.smirnowku.hwsc.dto.AssignmentDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;
import org.smirnowku.hwsc.util.PropertyFormatter;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class HomeworksTab extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private AssignmentService assignmentService;

    private Grid<AssignmentDto> toDoGrid;
    private Grid<AssignmentDto> submittedGrid;
    private Grid<AssignmentDto> completedGrid;

    public HomeworksTab() {
        toDoGrid = new Grid<>("TO DO");
        toDoGrid.addColumn(assignment -> assignment.getHomework().getClassroom().getName()).setCaption("Classroom");
        toDoGrid.addColumn(assignment -> assignment.getHomework().getName()).setCaption("Name");
        toDoGrid.addColumn(assignment -> assignment.getHomework().getDescription()).setCaption("Description");
        toDoGrid.addColumn(assignment -> PropertyFormatter.format(assignment.getHomework().getDeadline())).setCaption("Deadline");
        toDoGrid.addItemClickListener(this::navToAssignment);

        submittedGrid = new Grid<>("Submitted");
        submittedGrid.addColumn(assignment -> assignment.getHomework().getClassroom().getName()).setCaption("Classroom");
        submittedGrid.addColumn(assignment -> assignment.getHomework().getName()).setCaption("Name");
        submittedGrid.addColumn(assignment -> assignment.getScore() < 0 ? "" : assignment.getScore()).setCaption("Score");
        submittedGrid.addItemClickListener(this::navToAssignment);

        completedGrid = new Grid<>("Completed");
        completedGrid.addColumn(assignment -> assignment.getHomework().getClassroom().getName()).setCaption("Classroom");
        completedGrid.addColumn(assignment -> assignment.getHomework().getName()).setCaption("Name");
        completedGrid.addColumn(AssignmentDto::getScore).setCaption("Score");
        completedGrid.addItemClickListener(this::navToAssignment);

        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        toDoGrid.setSizeFull();
        submittedGrid.setSizeFull();
        completedGrid.setSizeFull();

        addComponents(toDoGrid, submittedGrid, completedGrid);
    }

    public void refresh() {
        toDoGrid.setItems(assignmentService.getToDo(authenticationService.getUsername()));
        submittedGrid.setItems(assignmentService.getSubmitted(authenticationService.getUsername()));
        completedGrid.setItems(assignmentService.getCompleted(authenticationService.getUsername()));
    }

    private void navToAssignment(Grid.ItemClick<AssignmentDto> itemClick) {
        if (itemClick.getMouseEventDetails().isDoubleClick()) {
            AssignmentDto assignment = itemClick.getItem();
            UI.getCurrent().getNavigator().navigateTo(Views.assignment(assignment.getId()));
        }
    }
}
