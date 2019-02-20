package hwsc.ui.classroom;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import hwsc.dto.ClassroomDto;
import hwsc.dto.UserDto;

@UIScope
@SpringComponent
public class TeachersTab extends VerticalLayout {

    private final Grid<UserDto> teachersGrid;

    public TeachersTab() {
        teachersGrid = new Grid<>();
        teachersGrid.addColumn(UserDto::getName).setCaption("Name");
        addComponents(teachersGrid);
    }

    public void refresh(ClassroomDto classroom) {
        teachersGrid.setItems(classroom.getTeachers());
    }
}
