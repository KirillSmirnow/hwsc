package hwsc.ui.dialog.addmembers;

import com.vaadin.ui.*;
import hwsc.ui.classroom.actions.MemberRole;
import hwsc.ui.dialog.CloseDialogListener;

import java.util.Arrays;

public class AddMembersDialogLayout extends VerticalLayout {

    private final AddMemberListener addMemberListener;

    private final TextField usernameField;
    private final RadioButtonGroup<String> radioButtonGroup;

    public AddMembersDialogLayout(AddMemberListener addMemberListener, CloseDialogListener closeDialogListener) {
        this.addMemberListener = addMemberListener;
        usernameField = new TextField("Enter username");
        usernameField.setWidth(300, Unit.PIXELS);
        radioButtonGroup = new RadioButtonGroup<>("Role", Arrays.asList(MemberRole.STUDENT.toString(), MemberRole.TEACHER.toString()));
        radioButtonGroup.setSelectedItem(MemberRole.STUDENT.toString());
        Button addMemberButton = new Button("Add", this::onAddMemberButtonClick);
        Button closeButton = new Button("Close", clickEvent -> closeDialogListener.onClose());
        HorizontalLayout buttonsLayout = new HorizontalLayout(addMemberButton, closeButton);
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(usernameField, radioButtonGroup, buttonsLayout);
    }

    private void onAddMemberButtonClick(Button.ClickEvent clickEvent) {
        if (radioButtonGroup.getValue().equals(MemberRole.STUDENT.toString())) {
            if (addMemberListener.onAddStudent(usernameField.getValue())) usernameField.clear();
        }
        if (radioButtonGroup.getValue().equals(MemberRole.TEACHER.toString())) {
            if (addMemberListener.onAddTeacher(usernameField.getValue())) usernameField.clear();
        }
    }
}
