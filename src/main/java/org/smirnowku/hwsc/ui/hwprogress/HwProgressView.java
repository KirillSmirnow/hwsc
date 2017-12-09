package org.smirnowku.hwsc.ui.hwprogress;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.service.HomeworkProgressService;
import org.smirnowku.hwsc.dto.HomeworkProgressDto;
import org.smirnowku.hwsc.ui.Views;

import javax.annotation.Resource;
import java.util.List;

@SpringView(name = Views.HW_PROGRESS)
public class HwProgressView extends VerticalLayout implements View {

    @Resource
    private HomeworkProgressService homeworkProgressService;

    private Grid<HomeworkProgressDto> progressGrid;

    public HwProgressView() {
        progressGrid = new Grid<>();
        progressGrid.addColumn(hp -> hp.getStudent().getName()).setCaption("Student");
        progressGrid.addColumn(HomeworkProgressDto::getStatus).setCaption("Status");
        progressGrid.addColumn(hp -> hp.getChecker() == null ? "" : hp.getChecker().getName()).setCaption("Checker");
        progressGrid.addColumn(hp -> hp.getScore() < 0 ? "" : hp.getScore()).setCaption("Score");
        progressGrid.setWidth(100, Unit.PERCENTAGE);
        addComponents(progressGrid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            refresh(Long.valueOf(viewChangeEvent.getParameters()));
        } catch (NumberFormatException e) {
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        }
    }

    private void refresh(Long id) {
        List<HomeworkProgressDto> homeworkProgress;
        try {
            homeworkProgress = homeworkProgressService.get(id);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
            return;
        }
        progressGrid.setItems(homeworkProgress);
        progressGrid.setHeightByRows(homeworkProgress.size());
    }
}
