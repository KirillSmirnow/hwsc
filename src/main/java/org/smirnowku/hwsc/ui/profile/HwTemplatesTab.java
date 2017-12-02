package org.smirnowku.hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.model.HomeworkTemplate;
import org.smirnowku.hwsc.core.service.impl.HomeworkTemplateService;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class HwTemplatesTab extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private HomeworkTemplateService homeworkTemplateService;

    private Grid<HomeworkTemplate> templatesGrid;

    public HwTemplatesTab() {
        templatesGrid = new Grid<>();
        templatesGrid.addColumn(HomeworkTemplate::getName).setCaption("Name");
        templatesGrid.addColumn(HomeworkTemplate::getDescription).setCaption("Description");
        templatesGrid.addItemClickListener(this::navToTemplate);

        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        templatesGrid.setSizeFull();

        addComponents(templatesGrid);
    }

    public void refresh() {
        templatesGrid.setItems(homeworkTemplateService.get(authenticationService.getUser().getUsername()));
    }

    private void navToTemplate(Grid.ItemClick<HomeworkTemplate> itemClick) {
        if (itemClick.getMouseEventDetails().isDoubleClick()) {
            HomeworkTemplate template = itemClick.getItem();
            UI.getCurrent().getNavigator().navigateTo(Views.hwTemplate(template.getId()));
        }
    }
}
