package org.smirnowku.hwsc.ui.profile.newclassroom;

import org.smirnowku.hwsc.ui.dialog.AbstractDialog;

public class NewClassroomDialog extends AbstractDialog {

    private NewClassroomLayout layout;

    public NewClassroomDialog(CreateClassroomListener createClassroomListener) {
        super("New Classroom");
        layout = new NewClassroomLayout(createClassroomListener, this);
        setContent(layout);
        setWidth(500, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
    }
}
