package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.util.PropertyFormatter;

import java.util.List;

@UIScope
@SpringComponent
public class HomeworksLayout extends VerticalLayout {

    private final Grid<HomeworkDto> homeworkGrid;

    public HomeworksLayout() {
        Label titleLabel = new Label("Homework");
        homeworkGrid = new Grid<>();
        homeworkGrid.addColumn(HomeworkDto::getName).setCaption("Name");
        homeworkGrid.addColumn(homework -> PropertyFormatter.format(homework.getDeadline())).setCaption("Deadline");
        homeworkGrid.addColumn(HomeworkDto::getStatus).setCaption("Status");
        homeworkGrid.addItemClickListener(this::navToAssignment);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(titleLabel, homeworkGrid);
    }

    public void refresh(List<HomeworkDto> homeworks) {
        homeworkGrid.setItems(homeworks);
    }

    private void navToAssignment(Grid.ItemClick<HomeworkDto> itemClick) {
        if (itemClick.getMouseEventDetails().isDoubleClick()) {
            HomeworkDto homework = itemClick.getItem();
            UI.getCurrent().getNavigator().navigateTo(Views.hwProgress(homework.getId()));
        }
    }
}
