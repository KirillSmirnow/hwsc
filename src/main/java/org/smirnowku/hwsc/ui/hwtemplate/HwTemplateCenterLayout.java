package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.dto.TaskTemplateDto;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class HwTemplateCenterLayout extends HorizontalLayout {

    @Resource
    private TaskTemplateLayout taskTemplateLayout;

    private Grid<TaskTemplateDto> tasksGrid;

    public HwTemplateCenterLayout() {
        tasksGrid = new Grid<>();
        tasksGrid.addColumn(TaskTemplateDto::getName).setCaption("Tasks");
        tasksGrid.addItemClickListener(this::openTask);
    }

    @PostConstruct
    public void init() {
        setSizeFull();
        addComponents(tasksGrid, taskTemplateLayout);
    }

    public void refresh(HomeworkTemplateDto template) {
        tasksGrid.setItems(template.getTaskTemplates());
    }

    private void openTask(Grid.ItemClick<TaskTemplateDto> itemClick) {
        TaskTemplateDto taskTemplate = itemClick.getItem();
        taskTemplateLayout.refresh(taskTemplate);
    }
}
