package org.smirnowku.hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.service.impl.HomeworkTemplateService;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;
import org.smirnowku.hwsc.ui.profile.newtemplate.NewHwTemplateDialog;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class HwTemplatesTab extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private HomeworkTemplateService homeworkTemplateService;

    private Button newTemplateButton;
    private Grid<HomeworkTemplateDto> templatesGrid;

    public HwTemplatesTab() {
        newTemplateButton = new Button("New Template");
        newTemplateButton.addClickListener(clickEvent -> newTemplate());

        templatesGrid = new Grid<>();
        templatesGrid.addColumn(HomeworkTemplateDto::getName).setCaption("Name");
        templatesGrid.addColumn(HomeworkTemplateDto::getDescription).setCaption("Description");
        templatesGrid.addItemClickListener(this::navToTemplate);

        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        templatesGrid.setSizeFull();

        addComponents(newTemplateButton, templatesGrid);
        setComponentAlignment(newTemplateButton, Alignment.TOP_RIGHT);
    }

    public void refresh() {
        templatesGrid.setItems(homeworkTemplateService.get(authenticationService.getUsername()));
    }

    private void navToTemplate(Grid.ItemClick<HomeworkTemplateDto> itemClick) {
        if (itemClick.getMouseEventDetails().isDoubleClick()) {
            HomeworkTemplateDto template = itemClick.getItem();
            UI.getCurrent().getNavigator().navigateTo(Views.hwTemplate(template.getId()));
        }
    }

    private void newTemplate() {
        NewHwTemplateDialog dialog = new NewHwTemplateDialog(this::createTemplate);
        dialog.showDialog();
    }

    private boolean createTemplate(String name, String description) {
        HomeworkTemplateDto dto = new HomeworkTemplateDto(name, description);
        try {
            homeworkTemplateService.create(authenticationService.getUsername(), dto);
            refresh();
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
