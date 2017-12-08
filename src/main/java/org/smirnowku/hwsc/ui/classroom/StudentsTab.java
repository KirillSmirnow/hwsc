package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.dto.UserDto;

@UIScope
@SpringComponent
public class StudentsTab extends VerticalLayout {

    private final Grid<UserDto> studentsGrid;

    public StudentsTab() {
        studentsGrid = new Grid<>();
        studentsGrid.addColumn(UserDto::getName).setCaption("Name");
        addComponents(studentsGrid);
    }

    public void refresh(ClassroomDto classroom) {
        studentsGrid.setItems(classroom.getStudents());
    }
}
