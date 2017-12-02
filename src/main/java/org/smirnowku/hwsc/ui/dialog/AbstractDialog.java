package org.smirnowku.hwsc.ui.dialog;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public abstract class AbstractDialog extends Window implements CloseDialogListener {

    public AbstractDialog(String caption) {
        super(caption);
        setResizable(false);
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
    public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass();
    }
}
