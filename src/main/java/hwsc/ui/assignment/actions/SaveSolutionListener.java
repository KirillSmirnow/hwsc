package hwsc.ui.assignment.actions;

import hwsc.dto.TaskSolutionDto;

public interface SaveSolutionListener {

    boolean onSave(TaskSolutionDto taskSolution);
}
