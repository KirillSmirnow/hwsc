package org.smirnowku.hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.service.impl.AssignmentService;
import org.smirnowku.hwsc.dto.AssignmentDto;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

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
        toDoGrid.addColumn(assignment -> assignment.getHomework().getDeadline()).setCaption("Deadline");

        submittedGrid = new Grid<>("Submitted");
        submittedGrid.addColumn(assignment -> assignment.getHomework().getClassroom().getName()).setCaption("Classroom");
        submittedGrid.addColumn(assignment -> assignment.getHomework().getName()).setCaption("Name");
        submittedGrid.addColumn(assignment -> assignment.getScore() < 0 ? "" : assignment.getScore()).setCaption("Score");

        completedGrid = new Grid<>("Completed");
        completedGrid.addColumn(assignment -> assignment.getHomework().getClassroom().getName()).setCaption("Classroom");
        completedGrid.addColumn(assignment -> assignment.getHomework().getName()).setCaption("Name");
        completedGrid.addColumn(AssignmentDto::getScore).setCaption("Score");

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
}
