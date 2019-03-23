package hwsc.dto;

import hwsc.model.Assignment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class AssignmentDto extends BaseDto {

    private UserDto student;
    private HomeworkDto homework;
    private HomeworkSolutionDto homeworkSolution;
    private Assignment.Status status;
    private Integer score;

    private AssignmentDto(long id, LocalDateTime created, LocalDateTime updated, UserDto student,
                          HomeworkDto homework, HomeworkSolutionDto homeworkSolution,
                          Assignment.Status status, Integer score) {
        super(id, created, updated);
        this.student = student;
        this.homework = homework;
        this.homeworkSolution = homeworkSolution;
        this.status = status;
        this.score = score;
    }

    public static AssignmentDto of(Assignment assignment) {
        return new AssignmentDto(assignment.getId(), assignment.getCreated(), assignment.getUpdated(),
                UserDto.of(assignment.getStudent()), HomeworkDto.of(assignment.getHomework()),
                HomeworkSolutionDto.of(assignment.getHomeworkSolution()),
                assignment.getStatus(), assignment.getScore());
    }
}
