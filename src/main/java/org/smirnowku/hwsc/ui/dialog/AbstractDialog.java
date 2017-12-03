package org.smirnowku.hwsc.ui.dialog;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public abstract class AbstractDialog extends Window implements CloseDialogListener {

    public AbstractDialog(String caption) {
        super(caption);
        setResizable(false);
        setModal(true);
        center();
    }

    public void showDialog() {
        if (UI.getCurrent().getWindows().stream().noneMatch(this::equals))
            UI.getCurrent().addWindow(this);
    }

    @Override
    public void onClose() {
        close();
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof AbstractDialog;
    }
}
