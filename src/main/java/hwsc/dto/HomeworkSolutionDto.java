package hwsc.dto;

import hwsc.model.HomeworkSolution;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class HomeworkSolutionDto extends BaseDto {

    private List<TaskSolutionDto> taskSolutions;

    private HomeworkSolutionDto(long id, LocalDateTime created, LocalDateTime updated,
                                List<TaskSolutionDto> taskSolutions) {
        super(id, created, updated);
        this.taskSolutions = taskSolutions;
    }

    public static HomeworkSolutionDto of(HomeworkSolution solution) {
        return new HomeworkSolutionDto(solution.getId(), solution.getCreated(), solution.getUpdated(),
                solution.getTaskSolutions().stream().map(TaskSolutionDto::of).collect(Collectors.toList()));
    }
}
