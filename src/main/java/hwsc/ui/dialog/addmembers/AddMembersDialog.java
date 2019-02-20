package hwsc.ui.dialog.addmembers;

import hwsc.ui.dialog.AbstractDialog;

public class AddMembersDialog extends AbstractDialog {

    public AddMembersDialog(AddMemberListener addMemberListener) {
        super("Add Members");
        setContent(new AddMembersDialogLayout(addMemberListener, this));
    }
}
