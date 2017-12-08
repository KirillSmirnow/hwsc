package org.smirnowku.hwsc.ui.assignment.actions;

import org.smirnowku.hwsc.dto.TaskSolutionDto;

public interface SaveSolutionListener {

    boolean onSave(TaskSolutionDto taskSolution);
}
