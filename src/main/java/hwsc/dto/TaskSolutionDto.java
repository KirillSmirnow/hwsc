package hwsc.dto;

import hwsc.model.TaskSolution;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class TaskSolutionDto extends BaseDto {

    private String link;

    private TaskSolutionDto(long id, LocalDateTime created, LocalDateTime updated, String link) {
        super(id, created, updated);
        this.link = link;
    }

    public static TaskSolutionDto of(TaskSolution taskSolution) {
        return new TaskSolutionDto(taskSolution.getId(), taskSolution.getCreated(),
                taskSolution.getUpdated(), taskSolution.getLink());
    }
}
