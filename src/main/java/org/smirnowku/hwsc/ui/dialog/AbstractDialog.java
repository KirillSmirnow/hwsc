package org.smirnowku.hwsc.ui.dialog;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public abstract class AbstractDialog extends Window implements CloseDialogListener {

    public AbstractDialog(String caption) {
        super(caption);
        setDraggable(false);
        setResizable(false);
        setModal(true);
        setWidth(500, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        center();
    }

    public void showDialog() {
        UI.getCurrent().addWindow(this);
    }

    @Override
    public void onClose() {
        close();
    }
}
