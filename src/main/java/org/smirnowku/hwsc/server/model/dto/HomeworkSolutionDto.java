package org.smirnowku.hwsc.server.model.dto;

import org.smirnowku.hwsc.server.exception.IllegalArgumentException;

import java.util.List;

public class HomeworkSolutionDto {

    private List<TaskSolutionDto> taskSolutions;

    public List<TaskSolutionDto> getTaskSolutions() {
        if (PropertyValidator.isEmpty(taskSolutions))
            throw new IllegalArgumentException("Task solutions cannot be empty");
        return taskSolutions;
    }

    @Override
    public String toString() {
        return "HomeworkSolutionDto{" +
                "taskSolutions=" + taskSolutions +
                '}';
    }
}
