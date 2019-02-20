package hwsc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HomeworkSolutionDto extends BaseDto {

    private List<TaskSolutionDto> taskSolutions;

    public HomeworkSolutionDto(long id, Date created, Date updated, List<TaskSolutionDto> taskSolutions) {
        super(id, created, updated);
        this.taskSolutions = taskSolutions;
    }
}
