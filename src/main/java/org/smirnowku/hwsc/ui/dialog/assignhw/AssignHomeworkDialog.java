package org.smirnowku.hwsc.ui.dialog.assignhw;

import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.ui.dialog.AbstractDialog;

import java.util.List;

public class AssignHomeworkDialog extends AbstractDialog {

    public AssignHomeworkDialog(String title, List<ClassroomDto> classrooms, AssignListener assignListener) {
        super(title);
        setContent(new AssignHomeworkDialogLayout(classrooms, assignListener, this));
    }
}
