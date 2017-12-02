package org.smirnowku.hwsc.ui.profile.newtemplate;

import org.smirnowku.hwsc.ui.dialog.AbstractDialog;

public class NewHwTemplateDialog extends AbstractDialog {

    private NewHwTemplateLayout layout;

    public NewHwTemplateDialog(CreateHwTemplateListener createHwTemplateListener) {
        super("New Template");
        layout = new NewHwTemplateLayout(createHwTemplateListener, this);
        setContent(layout);
        setWidth(500, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
    }
}
