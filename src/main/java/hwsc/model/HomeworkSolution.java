package hwsc.model;

import hwsc.dto.HomeworkSolutionDto;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class HomeworkSolution extends BaseEntity {

    @OneToMany
    private List<TaskSolution> taskSolutions;

    public HomeworkSolution() {
    }

    public List<TaskSolution> getTaskSolutions() {
        return taskSolutions;
    }

    public void setTaskSolutions(List<TaskSolution> taskSolutions) {
        this.taskSolutions = taskSolutions;
    }

    public HomeworkSolutionDto toDto() {
        return new HomeworkSolutionDto(getId(), getCreated(), getUpdated(),
                taskSolutions.stream().map(TaskSolution::toDto).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "HomeworkSolution{}";
    }
}
