package hwsc.dto;

import hwsc.model.Homework;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(callSuper = true)
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

    private HomeworkDto(long id, LocalDateTime created, LocalDateTime updated, ClassroomDto classroom,
                        List<TaskDto> tasks, String name, String description, Homework.Status status,
                        LocalDateTime deadline, Integer subgroupSize) {
        super(id, created, updated);
        this.classroom = classroom;
        this.tasks = tasks;
        this.name = name;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.subgroupSize = subgroupSize;
    }

    public static HomeworkDto of(Homework homework) {
        return new HomeworkDto(homework.getId(), homework.getCreated(),
                homework.getUpdated(), ClassroomDto.of(homework.getClassroom()),
                homework.getTasks().stream().map(TaskDto::of).collect(Collectors.toList()),
                homework.getName(), homework.getDescription(), homework.getStatus(),
                homework.getDeadline(), homework.getSubgroupSize());
    }
}
