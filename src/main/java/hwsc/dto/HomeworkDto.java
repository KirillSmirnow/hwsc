package hwsc.dto;

import hwsc.model.Homework;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HomeworkDto extends BaseDto {

    private ClassroomDto classroom;
    private List<TaskDto> tasks;
    private String name;
    private String description;
    private Homework.Status status;
    private LocalDateTime deadline;
    private Integer subgroupSize;

    public HomeworkDto(LocalDateTime deadline, Integer subgroupSize) {
        this.deadline = deadline;
        this.subgroupSize = subgroupSize;
    }

    public HomeworkDto(long id, Date created, Date updated, ClassroomDto classroom,
                       List<TaskDto> tasks, String name, String description,
                       Homework.Status status, LocalDateTime deadline, Integer subgroupSize) {
        super(id, created, updated);
        this.classroom = classroom;
        this.tasks = tasks;
        this.name = name;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.subgroupSize = subgroupSize;
    }
}
