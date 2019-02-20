package hwsc.ui.dialog.assignhw;

import hwsc.dto.HomeworkDto;

public interface AssignListener {

    boolean onAssign(Long classroomId, HomeworkDto homework);
}
