package org.smirnowku.hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.service.impl.ClassroomService;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;
import org.smirnowku.hwsc.ui.profile.newclassroom.NewClassroomDialog;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class ClassroomsTab extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private ClassroomService classroomService;

    private Button newClassroomButton;
    private Grid<ClassroomDto> asStudentGrid;
    private Grid<ClassroomDto> asTeacherGrid;

    public ClassroomsTab() {
        newClassroomButton = new Button("New Classroom", clickEvent -> newClassroom());

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

        addComponents(asStudentGrid, asTeacherGrid, newClassroomButton);
        setComponentAlignment(newClassroomButton, Alignment.BOTTOM_RIGHT);
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

    private void newClassroom() {
        NewClassroomDialog dialog = new NewClassroomDialog(this::createClassroom);
        dialog.showDialog();
    }

    private boolean createClassroom(String name, String description) {
        ClassroomDto dto = new ClassroomDto(name, description);
        try {
            classroomService.create(authenticationService.getUsername(), dto);
            refresh();
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
