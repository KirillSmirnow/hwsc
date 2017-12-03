package org.smirnowku.hwsc.ui.hwtemplate.actions;

import org.smirnowku.hwsc.dto.TaskTemplateDto;

public interface AddTaskListener {

    boolean onAddTask(TaskTemplateDto taskTemplate);
}
