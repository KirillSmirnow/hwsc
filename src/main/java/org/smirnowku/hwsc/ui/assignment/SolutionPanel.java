package org.smirnowku.hwsc.ui.assignment;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.smirnowku.hwsc.dto.TaskSolutionDto;
import org.smirnowku.hwsc.ui.assignment.actions.SaveSolutionListener;
import org.smirnowku.hwsc.ui.dialog.prompt.PromptDialog;
import org.smirnowku.hwsc.util.PropertyValidator;

@UIScope
@SpringComponent
public class SolutionPanel extends HorizontalLayout {

    private SaveSolutionListener saveSolutionListener;

    private TaskSolutionDto taskSolution;
    private Label linkLabel;
    private Button editButton;

    public SolutionPanel() {
        linkLabel = new Label("", ContentMode.HTML);
        editButton = new Button("Edit", clickEvent -> edit());
        editButton.setVisible(false);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(linkLabel, editButton);
    }

    public void refresh(TaskSolutionDto taskSolution, boolean editable) {
        this.taskSolution = taskSolution;
        this.editButton.setVisible(editable);
        if (PropertyValidator.isEmpty(taskSolution.getLink())) {
            linkLabel.setValue("Provide link to solution here");
        } else {
            linkLabel.setValue(String.format("<b><a href=\"%s\">Solution Link</a></b>", taskSolution.getLink()));
        }
    }

    public void setSaveSolutionListener(SaveSolutionListener saveSolutionListener) {
        this.saveSolutionListener = saveSolutionListener;
    }

    private void edit() {
        String link = taskSolution.getLink();
        if (PropertyValidator.isEmpty(link)) link = "http://";
        PromptDialog dialog = new PromptDialog("Edit Solution Link", link, "Link",
                "Save", value -> {
            taskSolution.setLink(value);
            return saveSolutionListener.onSave(taskSolution);
        });
        dialog.showDialog();
    }
}
