package hwsc.dto;

import hwsc.model.TaskTemplate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class TaskTemplateDto extends BaseDto {

    private String name;
    private String description;

    public TaskTemplateDto(String name) {
        this.name = name;
    }

    private TaskTemplateDto(long id, LocalDateTime created, LocalDateTime updated,
                            String name, String description) {
        super(id, created, updated);
        this.name = name;
        this.description = description;
    }

    public static TaskTemplateDto of(TaskTemplate taskTemplate) {
        return new TaskTemplateDto(taskTemplate.getId(), taskTemplate.getCreated(), taskTemplate.getUpdated(),
                taskTemplate.getName(), taskTemplate.getDescription());
    }
}
