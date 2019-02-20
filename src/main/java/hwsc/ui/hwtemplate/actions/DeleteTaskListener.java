package hwsc.ui.hwtemplate.actions;

import hwsc.dto.TaskTemplateDto;

public interface DeleteTaskListener {

    boolean onDeleteTask(TaskTemplateDto taskTemplate);
}
