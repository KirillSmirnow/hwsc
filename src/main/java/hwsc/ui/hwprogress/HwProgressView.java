package hwsc.ui.hwprogress;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import hwsc.HwscException;
import hwsc.dto.HomeworkDto;
import hwsc.dto.HomeworkProgressDto;
import hwsc.model.Homework;
import hwsc.service.HomeworkProgressService;
import hwsc.service.HomeworkService;
import hwsc.ui.Views;
import hwsc.ui.auth.AuthenticationService;
import hwsc.ui.dialog.confirm.ConfirmDialog;

import javax.annotation.Resource;
import java.util.List;

@SpringView(name = Views.HW_PROGRESS)
public class HwProgressView extends VerticalLayout implements View {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private HomeworkService homeworkService;

    @Resource
    private HomeworkProgressService homeworkProgressService;

    private long homeworkId;

    private Button finishButton;
    private Grid<HomeworkProgressDto> progressGrid;

    public HwProgressView() {
        finishButton = new Button("Finish Homework", clickEvent -> onFinish());
        progressGrid = new Grid<>();
        progressGrid.addColumn(hp -> hp.getAssignment().getStudent().getName()).setCaption("Student");
        progressGrid.addColumn(hp -> hp.getAssignment().getStatus()).setCaption("Status");
        progressGrid.addColumn(hp -> hp.getChecker() == null ? "" : hp.getChecker().getName()).setCaption("Checker");
        progressGrid.addColumn(hp -> hp.getAssignment().getScore() < 0 ? "" :
                hp.getAssignment().getScore()).setCaption("Score");
        progressGrid.setWidth(100, Unit.PERCENTAGE);
        addComponents(finishButton, progressGrid);
        setComponentAlignment(finishButton, Alignment.TOP_RIGHT);
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
        this.homeworkId = id;
        HomeworkDto homework;
        List<HomeworkProgressDto> homeworkProgress;
        try {
            homework = homeworkService.get(id);
            homeworkProgress = homeworkProgressService.get(id);
        } catch (HwscException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
            return;
        }
        progressGrid.setItems(homeworkProgress);
        progressGrid.setHeightByRows(homeworkProgress.size());
        progressGrid.addItemClickListener(this::navToAssignment);
        finishButton.setVisible(homework.getStatus() != Homework.Status.INACTIVE);
    }

    private void onFinish() {
        ConfirmDialog dialog = new ConfirmDialog("Finish Homework", "Are you sure you want to finish this homework?",
                "Finish", () -> {
            try {
                homeworkService.finish(authenticationService.getUsername(), homeworkId);
            } catch (HwscException e) {
                Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
                return false;
            }
            refresh(homeworkId);
            return true;
        });
        dialog.showDialog();
    }

    private void navToAssignment(Grid.ItemClick<HomeworkProgressDto> itemClick) {
        if (itemClick.getMouseEventDetails().isDoubleClick()) {
            HomeworkProgressDto homeworkProgress = itemClick.getItem();
            UI.getCurrent().getNavigator().navigateTo(Views.assignment(homeworkProgress.getAssignment().getId()));
        }
    }
}
