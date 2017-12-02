package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.model.Classroom;
import org.smirnowku.hwsc.core.model.User;

@UIScope
@SpringComponent
public class TeachersTab extends VerticalLayout {

    private Grid<User> teachersGrid;

    public TeachersTab() {
        teachersGrid = new Grid<>();
        teachersGrid.addColumn(User::getName).setCaption("Name");
        addComponents(teachersGrid);
    }

    public void refresh(Classroom classroom) {
        teachersGrid.setItems(classroom.getTeachers());
    }
}
