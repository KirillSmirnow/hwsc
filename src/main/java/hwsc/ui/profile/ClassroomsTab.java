package hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import hwsc.HwscException;
import hwsc.dto.ClassroomDto;
import hwsc.service.ClassroomService;
import hwsc.ui.Views;
import hwsc.ui.auth.AuthenticationService;
import hwsc.ui.dialog.namedescription.NameDescriptionDialog;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class ClassroomsTab extends VerticalLayout {

    private final Grid<ClassroomDto> asStudentGrid;
    private final Grid<ClassroomDto> asTeacherGrid;
    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private ClassroomService classroomService;

    public ClassroomsTab() {
        Button newClassroomButton = new Button("New Classroom", clickEvent -> newClassroom());

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
        NameDescriptionDialog dialog = new NameDescriptionDialog("New Classroom", null, null,
                "Create", this::createClassroom);
        dialog.showDialog();
    }

    private boolean createClassroom(String name, String description) {
        ClassroomDto classroom = new ClassroomDto(name, description);
        try {
            classroomService.create(authenticationService.getUsername(), classroom);
            refresh();
        } catch (HwscException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
