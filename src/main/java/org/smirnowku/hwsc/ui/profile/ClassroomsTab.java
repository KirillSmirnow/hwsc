package org.smirnowku.hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.service.impl.ClassroomService;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class ClassroomsTab extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private ClassroomService classroomService;

    private Grid<ClassroomDto> asStudentGrid;
    private Grid<ClassroomDto> asTeacherGrid;

    public ClassroomsTab() {
        asStudentGrid = new Grid<>("As student");
        asStudentGrid.addColumn(ClassroomDto::getName).setCaption("Name");
        asStudentGrid.addColumn(ClassroomDto::getDescription).setCaption("Description");
        asStudentGrid.addItemClickListener(this::navToClassroom);

        asTeacherGrid = new Grid<>("As teacher");
        asTeacherGrid.addColumn(ClassroomDto::getName).setCaption("Name");
        asTeacherGrid.addColumn(ClassroomDto::getDescription).setCaption("Description");
        asTeacherGrid.addItemClickListener(this::navToClassroom);

        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        asStudentGrid.setSizeFull();
        asTeacherGrid.setSizeFull();

        addComponents(asStudentGrid, asTeacherGrid);
    }

    public void refresh() {
        asStudentGrid.setItems(classroomService.getClassroomsAsStudent(authenticationService.getUsername()));
        asTeacherGrid.setItems(classroomService.getClassroomsAsTeacher(authenticationService.getUsername()));
    }

    private void navToClassroom(Grid.ItemClick<ClassroomDto> itemClick) {
        if (itemClick.getMouseEventDetails().isDoubleClick()) {
            ClassroomDto classroom = itemClick.getItem();
            UI.getCurrent().getNavigator().navigateTo(Views.classroom(classroom.getId()));
        }
    }
}
