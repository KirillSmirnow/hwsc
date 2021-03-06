package hwsc.ui.dialog.namedescription;

import hwsc.ui.dialog.AbstractDialog;

public class NameDescriptionDialog extends AbstractDialog {

    public NameDescriptionDialog(String title, String name, String description, String actionButtonCaption,
                                 ActionListener actionListener) {
        super(title);
        setContent(new NameDescriptionDialogLayout(name, description, actionButtonCaption, actionListener, this));
    }
}
