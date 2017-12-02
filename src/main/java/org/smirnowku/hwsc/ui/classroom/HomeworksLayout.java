package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.dto.HomeworkDto;

import java.util.List;

@UIScope
@SpringComponent
public class HomeworksLayout extends VerticalLayout {

    private Label titleLabel;
    private Grid<HomeworkDto> homeworkGrid;

    public HomeworksLayout() {
        titleLabel = new Label("Homework");
        homeworkGrid = new Grid<>();
        homeworkGrid.addColumn(HomeworkDto::getName).setCaption("Name");
        homeworkGrid.addColumn(HomeworkDto::getDeadline).setCaption("Deadline");
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(titleLabel, homeworkGrid);
    }

    public void refresh(List<HomeworkDto> homeworks) {
        homeworkGrid.setItems(homeworks);
    }
}
