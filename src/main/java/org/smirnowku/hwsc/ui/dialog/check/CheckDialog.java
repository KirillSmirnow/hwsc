package org.smirnowku.hwsc.ui.dialog.check;

import org.smirnowku.hwsc.ui.dialog.AbstractDialog;

public class CheckDialog extends AbstractDialog {

    public CheckDialog(String title, CheckListener checkListener) {
        super(title);
        setContent(new CheckDialogLayout(checkListener, this));
    }
}
