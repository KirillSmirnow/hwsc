package hwsc.dto;

import hwsc.model.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class TaskDto extends BaseDto {

    private String name;
    private String description;

    private TaskDto(long id, LocalDateTime created, LocalDateTime updated,
                    String name, String description) {
        super(id, created, updated);
        this.name = name;
        this.description = description;
    }

    public static TaskDto of(Task task) {
        return new TaskDto(task.getId(), task.getCreated(), task.getUpdated(),
                task.getName(), task.getDescription());
    }
}
