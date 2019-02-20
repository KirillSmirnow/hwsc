package hwsc.dto;

import hwsc.model.Assignment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AssignmentDto extends BaseDto {

    private UserDto student;
    private HomeworkDto homework;
    private HomeworkSolutionDto homeworkSolution;
    private Assignment.Status status;
    private Integer score;

    public AssignmentDto(long id, Date created, Date updated, UserDto student,
                         HomeworkDto homework, HomeworkSolutionDto homeworkSolution,
                         Assignment.Status status, Integer score) {
        super(id, created, updated);
        this.student = student;
        this.homework = homework;
        this.homeworkSolution = homeworkSolution;
        this.status = status;
        this.score = score;
    }
}
