package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.model.Classroom;
import org.smirnowku.hwsc.core.model.User;

@UIScope
@SpringComponent
public class StudentsTab extends VerticalLayout {

    private Grid<User> studentsGrid;

    public StudentsTab() {
        studentsGrid = new Grid<>();
        studentsGrid.addColumn(User::getName).setCaption("Name");
        addComponents(studentsGrid);
    }

    public void refresh(Classroom classroom) {
        studentsGrid.setItems(classroom.getStudents());
    }
}
