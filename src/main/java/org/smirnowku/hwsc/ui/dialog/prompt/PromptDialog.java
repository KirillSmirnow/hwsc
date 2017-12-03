package org.smirnowku.hwsc.ui.dialog.prompt;

import org.smirnowku.hwsc.ui.dialog.AbstractDialog;

public class PromptDialog extends AbstractDialog {

    public PromptDialog(String title, String valueCaption, String confirmButtonCaption, PromptListener promptListener) {
        super(title);
        setContent(new PromptDialogLayout(valueCaption, confirmButtonCaption, "Cancel",
                promptListener, this));
    }
}
