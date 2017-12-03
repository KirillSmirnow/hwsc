package org.smirnowku.hwsc.ui.dialog.confirm;

import org.smirnowku.hwsc.ui.dialog.AbstractDialog;

public class ConfirmDialog extends AbstractDialog {

    public ConfirmDialog(String title, String message, String confirmButtonCaption, ConfirmListener confirmListener) {
        super(title);
        ConfirmDialogLayout confirmDialogLayout = new ConfirmDialogLayout(message, confirmButtonCaption,
                "Cancel", confirmListener, this);
        setContent(confirmDialogLayout);
        setWidth(50, Unit.PERCENTAGE);
        setHeight(50, Unit.PERCENTAGE);
    }
}
