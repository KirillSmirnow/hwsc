package org.smirnowku.hwsc.ui.assignment;

import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.dto.TaskDto;
import org.smirnowku.hwsc.ui.assignment.actions.SaveSolutionListener;
import org.smirnowku.hwsc.ui.assignment.actions.SelectTaskListener;

import java.util.Optional;

@UIScope
@SpringComponent
public class TaskPanel extends VerticalLayout implements SelectTaskListener {

    private SaveSolutionListener saveSolutionListener;

    private Label nameLabel;
    private Label descriptionLabel;

    public TaskPanel() {
        nameLabel = new Label();
        descriptionLabel = new Label("", ContentMode.HTML);

        nameLabel.setWidth(300, Unit.PIXELS);
        descriptionLabel.setWidth(90, Unit.PERCENTAGE);
        descriptionLabel.setHeight(600, Unit.PIXELS);

        setVisible(false);
        addComponents(nameLabel, descriptionLabel);
        setComponentAlignment(nameLabel, Alignment.TOP_CENTER);
        setComponentAlignment(descriptionLabel, Alignment.MIDDLE_CENTER);
    }

    public void setListeners(SaveSolutionListener saveSolutionListener) {
        this.saveSolutionListener = saveSolutionListener;
    }

    public void refresh(TaskDto task) {
        nameLabel.setValue(task.getName());
        descriptionLabel.setValue(task.getDescription());
    }

    @Override
    public void onSelect(SelectionEvent<TaskDto> selectionEvent) {
        Optional<TaskDto> task = selectionEvent.getFirstSelectedItem();
        setVisible(task.isPresent());
        task.ifPresent(this::refresh);
    }
}
