package hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import hwsc.dto.AssignmentDto;
import hwsc.service.AssignmentService;
import hwsc.ui.Views;
import hwsc.ui.auth.AuthenticationService;
import hwsc.util.PropertyFormatter;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class HomeworksTab extends VerticalLayout {

    private final Grid<AssignmentDto> toDoGrid;
    private final Grid<AssignmentDto> submittedGrid;
    private final Grid<AssignmentDto> completedGrid;
    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private AssignmentService assignmentService;

    public HomeworksTab() {
        toDoGrid = new Grid<>("TO DO");
        toDoGrid.addColumn(assignment -> assignment.getHomework().getClassroom().getName()).setCaption("Classroom");
        toDoGrid.addColumn(assignment -> assignment.getHomework().getName()).setCaption("Name");
        toDoGrid.addColumn(assignment -> assignment.getHomework().getDescription()).setCaption("Description");
        toDoGrid.addColumn(assignment -> PropertyFormatter.format(assignment.getHomework().getDeadline()))
                .setCaption("Deadline");
        toDoGrid.addItemClickListener(this::navToAssignment);

        submittedGrid = new Grid<>("Submitted");
        submittedGrid.addColumn(assignment -> assignment.getHomework().getClassroom().getName()).setCaption("Classroom");
        submittedGrid.addColumn(assignment -> assignment.getHomework().getName()).setCaption("Name");
        submittedGrid.addColumn(assignment -> assignment.getScore() < 0 ? "" : assignment.getScore())
                .setCaption("Score");
        submittedGrid.addItemClickListener(this::navToAssignment);

        completedGrid = new Grid<>("Completed");
        completedGrid.addColumn(assignment -> assignment.getHomework().getClassroom().getName()).setCaption("Classroom");
        completedGrid.addColumn(assignment -> assignment.getHomework().getName()).setCaption("Name");
        completedGrid.addColumn(assignment -> assignment.getScore() < 0 ? "" : assignment.getScore())
                .setCaption("Score");
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
