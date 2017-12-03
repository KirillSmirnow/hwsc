package org.smirnowku.hwsc.ui.dialog.assignhw;

import org.smirnowku.hwsc.dto.HomeworkDto;

public interface AssignListener {

    boolean onAssign(Long classroomId, HomeworkDto homework);
}
