package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.model.HomeworkTemplate;
import org.smirnowku.hwsc.core.service.impl.HomeworkTemplateService;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.HW_TEMPLATE)
public class HwTemplateView extends VerticalLayout implements View {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private HomeworkTemplateService homeworkTemplateService;

    @Resource
    private HwTemplateCenterLayout hwTemplateCenterLayout;

    private Label nameLabel;
    private Label descriptionLabel;

    public HwTemplateView() {
        nameLabel = new Label("Name", ContentMode.HTML);
        descriptionLabel = new Label("Description", ContentMode.HTML);
    }

    @PostConstruct
    public void init() {
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addComponents(nameLabel, descriptionLabel, hwTemplateCenterLayout);
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
            HomeworkTemplate template = homeworkTemplateService.get(authenticationService.getUser().getUsername(), id);
            refresh(template);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        }
    }

    private void refresh(HomeworkTemplate template) {
        nameLabel.setValue(String.format("<h1>%s</h1>", template.getName()));
        if (template.getDescription() == null || template.getDescription().isEmpty()) {
            descriptionLabel.setVisible(false);
        } else {
            descriptionLabel.setVisible(true);
            descriptionLabel.setValue(String.format("<i>%s</i>", template.getDescription()));
        }
        hwTemplateCenterLayout.refresh(template);
    }
}
