package org.smirnowku.hwsc.ui.assignment.actions;

import org.smirnowku.hwsc.dto.HomeworkSolutionDto;

public interface SaveSolutionListener {

    boolean onSave(HomeworkSolutionDto homeworkSolution);
}
