package hwsc.dto;

import java.util.Date;
import java.util.List;

public class HomeworkSolutionDto extends BaseDto {

    private List<TaskSolutionDto> taskSolutions;

    public HomeworkSolutionDto() {
    }

    public HomeworkSolutionDto(long id, Date created, Date updated, List<TaskSolutionDto> taskSolutions) {
        super(id, created, updated);
        this.taskSolutions = taskSolutions;
    }

    public List<TaskSolutionDto> getTaskSolutions() {
        return taskSolutions;
    }

    @Override
    public String toString() {
        return "HomeworkSolutionDto{" +
                "taskSolutions=" + taskSolutions +
                '}';
    }
}
