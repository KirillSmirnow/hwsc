package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.service.impl.ClassroomService;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.CLASSROOM)
public class ClassroomView extends VerticalLayout implements View {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private ClassroomService classroomService;

    @Resource
    private ClassroomCenterLayout classroomCenterLayout;

    private Label nameLabel;
    private Label descriptionLabel;

    public ClassroomView() {
        nameLabel = new Label("Name", ContentMode.HTML);
        descriptionLabel = new Label("Description", ContentMode.HTML);
    }

    @PostConstruct
    public void init() {
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addComponents(nameLabel, descriptionLabel, classroomCenterLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Long id;
        try {
            id = Long.valueOf(viewChangeEvent.getParameters());
        } catch (NumberFormatException e) {
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
            return;
        }
        try {
            ClassroomDto classroom = classroomService.get(authenticationService.getUsername(), id);
            refresh(classroom);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        }
    }

    private void refresh(ClassroomDto classroom) {
        nameLabel.setValue(String.format("<h1>%s</h1>", classroom.getName()));
        if (classroom.getDescription() == null || classroom.getDescription().isEmpty()) {
            descriptionLabel.setVisible(false);
        } else {
            descriptionLabel.setVisible(true);
            descriptionLabel.setValue(String.format("<i>%s</i>", classroom.getDescription()));
        }
        classroomCenterLayout.refresh(classroom);
    }
}
