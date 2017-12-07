package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.ui.classroom.actions.EditClassroomListener;
import org.smirnowku.hwsc.ui.dialog.addmembers.AddMemberListener;
import org.smirnowku.hwsc.ui.dialog.addmembers.AddMembersDialog;
import org.smirnowku.hwsc.ui.dialog.namedescription.NameDescriptionDialog;
import org.smirnowku.hwsc.util.PropertyValidator;

@UIScope
@SpringComponent
public class ClassroomHeader extends HorizontalLayout {

    private AddMemberListener addMemberListener;
    private EditClassroomListener editClassroomListener;

    private ClassroomDto classroom;

    private Label nameLabel;
    private Label descriptionLabel;

    public ClassroomHeader() {
        nameLabel = new Label("Name", ContentMode.HTML);
        descriptionLabel = new Label("Description", ContentMode.HTML);
        ClassroomOptionsBar classroomOptionsBar = new ClassroomOptionsBar(this::edit, this::addMembers);
        setSizeFull();
        addComponents(new VerticalLayout(nameLabel, descriptionLabel), classroomOptionsBar);
        setComponentAlignment(classroomOptionsBar, Alignment.MIDDLE_RIGHT);
    }

    public void setListeners(AddMemberListener addMemberListener, EditClassroomListener editClassroomListener) {
        this.addMemberListener = addMemberListener;
        this.editClassroomListener = editClassroomListener;
    }

    public void refresh(ClassroomDto classroom) {
        this.classroom = classroom;
        nameLabel.setValue(String.format("<h1>%s</h1>", classroom.getName()));
        if (PropertyValidator.isEmpty(classroom.getDescription())) {
            descriptionLabel.setVisible(false);
        } else {
            descriptionLabel.setVisible(true);
            descriptionLabel.setValue(String.format("<i>%s</i>", classroom.getDescription()));
        }
    }

    private void edit(Button.ClickEvent clickEvent) {
        NameDescriptionDialog dialog = new NameDescriptionDialog("Edit Info", classroom.getName(), classroom.getDescription(),
                "Save", (name, description) -> {
            classroom.setName(name);
            classroom.setDescription(description);
            return editClassroomListener.onEditClassroom(classroom);
        });
        dialog.showDialog();
    }

    private void addMembers(Button.ClickEvent clickEvent) {
        AddMembersDialog dialog = new AddMembersDialog(addMemberListener);
        dialog.showDialog();
    }
}
