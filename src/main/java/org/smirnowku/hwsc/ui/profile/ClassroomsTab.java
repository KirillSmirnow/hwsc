package org.smirnowku.hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.model.Classroom;
import org.smirnowku.hwsc.core.service.impl.ClassroomService;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class ClassroomsTab extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private ClassroomService classroomService;

    private Grid<Classroom> asStudentGrid;
    private Grid<Classroom> asTeacherGrid;

    public ClassroomsTab() {
        asStudentGrid = new Grid<>("As student");
        asStudentGrid.addColumn(Classroom::getName).setCaption("Name");
        asStudentGrid.addColumn(Classroom::getDescription).setCaption("Description");

        asTeacherGrid = new Grid<>("As teacher");
        asTeacherGrid.addColumn(Classroom::getName).setCaption("Name");
        asTeacherGrid.addColumn(Classroom::getDescription).setCaption("Description");

        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        asStudentGrid.setSizeFull();
        asTeacherGrid.setSizeFull();

        addComponents(asStudentGrid, asTeacherGrid);
    }

    public void refresh() {
        asStudentGrid.setItems(classroomService.getClassroomsAsStudent(authenticationService.getUser().getUsername()));
        asTeacherGrid.setItems(classroomService.getClassroomsAsTeacher(authenticationService.getUser().getUsername()));
    }
}
