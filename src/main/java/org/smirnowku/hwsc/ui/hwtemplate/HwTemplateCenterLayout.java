package org.smirnowku.hwsc.ui.hwtemplate;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import org.smirnowku.hwsc.core.model.HomeworkTemplate;
import org.smirnowku.hwsc.core.model.TaskTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class HwTemplateCenterLayout extends HorizontalLayout {

    @Resource
    private TaskTemplateLayout taskTemplateLayout;

    private Grid<TaskTemplate> tasksGrid;

    public HwTemplateCenterLayout() {
        tasksGrid = new Grid<>();
        tasksGrid.addColumn(TaskTemplate::getName).setCaption("Tasks");
        tasksGrid.addItemClickListener(this::openTask);
    }

    @PostConstruct
    public void init() {
        setSizeFull();
        addComponents(tasksGrid, taskTemplateLayout);
    }

    public void refresh(HomeworkTemplate template) {
        tasksGrid.setItems(template.getTaskTemplates());
    }

    private void openTask(Grid.ItemClick<TaskTemplate> itemClick) {
        TaskTemplate taskTemplate = itemClick.getItem();
        taskTemplateLayout.refresh(taskTemplate);
    }
}
