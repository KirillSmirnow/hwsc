package hwsc.ui.hwtemplate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import hwsc.HwscException;
import hwsc.dto.HomeworkTemplateDto;
import hwsc.dto.TaskTemplateDto;
import hwsc.service.HomeworkTemplateService;
import hwsc.ui.Views;
import hwsc.ui.auth.AuthenticationService;
import hwsc.ui.hwtemplate.actions.*;
import hwsc.util.PropertyValidator;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.HW_TEMPLATE)
public class HwTemplateView extends VerticalLayout implements View, AddTaskListener, DeleteHwTemplateListener,
        EditHwTemplateListener, EditTaskListener, DeleteTaskListener {

    private final Label nameLabel;
    private final Label descriptionLabel;
    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private HomeworkTemplateService homeworkTemplateService;
    @Resource
    private HwTemplateTasksLayout hwTemplateTasksLayout;
    private HomeworkTemplateDto homeworkTemplate;

    public HwTemplateView() {
        nameLabel = new Label("Name", ContentMode.HTML);
        descriptionLabel = new Label("Description", ContentMode.HTML);
    }

    @PostConstruct
    public void init() {
        addComponents(nameLabel, descriptionLabel, hwTemplateTasksLayout);
        setComponentAlignment(nameLabel, Alignment.TOP_CENTER);
        setComponentAlignment(descriptionLabel, Alignment.TOP_CENTER);
        setComponentAlignment(hwTemplateTasksLayout, Alignment.MIDDLE_CENTER);
        hwTemplateTasksLayout.setListeners(this, this, this, this, this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            refresh(Long.valueOf(viewChangeEvent.getParameters()));
        } catch (NumberFormatException e) {
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        }
    }

    @Override
    public boolean onAddTask(TaskTemplateDto taskTemplate) {
        homeworkTemplate.getTaskTemplates().add(taskTemplate);
        return onEditHwTemplate(homeworkTemplate);
    }

    @Override
    public boolean onEditTask(TaskTemplateDto taskTemplate) {
        homeworkTemplate.getTaskTemplates().set(homeworkTemplate.getTaskTemplates().indexOf(taskTemplate), taskTemplate);
        return onEditHwTemplate(homeworkTemplate);
    }

    @Override
    public boolean onDeleteTask(TaskTemplateDto taskTemplate) {
        homeworkTemplate.getTaskTemplates().remove(taskTemplate);
        return onEditHwTemplate(homeworkTemplate);
    }

    @Override
    public boolean onEditHwTemplate(HomeworkTemplateDto homeworkTemplate) {
        try {
            homeworkTemplateService.edit(authenticationService.getUsername(), homeworkTemplate.getId(), homeworkTemplate);
        } catch (HwscException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            refresh(homeworkTemplate.getId());
            return false;
        }
        refresh(homeworkTemplate.getId());
        return true;
    }

    @Override
    public boolean onDeleteHwTemplate() {
        try {
            homeworkTemplateService.delete(authenticationService.getUsername(), homeworkTemplate.getId());
        } catch (HwscException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            return false;
        }
        UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        return true;
    }

    private void refresh(Long id) {
        HomeworkTemplateDto homeworkTemplate;
        try {
            homeworkTemplate = homeworkTemplateService.get(authenticationService.getUsername(), id);
        } catch (HwscException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
            return;
        }
        this.homeworkTemplate = homeworkTemplate;
        refresh();
    }

    private void refresh() {
        nameLabel.setValue(String.format("<h1>%s</h1>", homeworkTemplate.getName()));
        if (PropertyValidator.isEmpty(homeworkTemplate.getDescription())) {
            descriptionLabel.setVisible(false);
        } else {
            descriptionLabel.setVisible(true);
            descriptionLabel.setValue(String.format("<i>%s</i>", homeworkTemplate.getDescription()));
        }
        hwTemplateTasksLayout.refresh(homeworkTemplate);
    }
}
