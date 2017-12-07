package org.smirnowku.hwsc.ui.assignment;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.smirnowku.hwsc.dto.TaskDto;
import org.smirnowku.hwsc.ui.assignment.actions.SelectTaskListener;

@UIScope
@SpringComponent
public class AssignmentSidePanel extends VerticalLayout {

    private ViewMode viewMode;

    private Grid<TaskDto> tasksGrid;

    public AssignmentSidePanel() {
        tasksGrid = new Grid<>();
        tasksGrid.addColumn(TaskDto::getName).setCaption("Tasks");

        tasksGrid.setWidth(100, Unit.PERCENTAGE);
        tasksGrid.setHeight(600, Unit.PIXELS);

        addComponents(tasksGrid);
    }

    public void refresh(HomeworkDto homework, ViewMode viewMode) {
        this.viewMode = viewMode;
        tasksGrid.setItems(homework.getTasks());
    }

    public void setListeners(SelectTaskListener selectTaskListener) {
        tasksGrid.addSelectionListener(selectTaskListener::onSelect);
    }
}
