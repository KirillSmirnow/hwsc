package hwsc.ui.dialog.confirm;

import hwsc.ui.dialog.AbstractDialog;

public class ConfirmDialog extends AbstractDialog {

    public ConfirmDialog(String title, String message, String confirmButtonCaption, ConfirmListener confirmListener) {
        super(title);
        setContent(new ConfirmDialogLayout(message, confirmButtonCaption, "Cancel",
                confirmListener, this));
    }
}
