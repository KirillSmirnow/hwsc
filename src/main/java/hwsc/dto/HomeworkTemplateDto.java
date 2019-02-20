package hwsc.dto;

import hwsc.model.HomeworkTemplate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class HomeworkTemplateDto extends BaseDto {

    private UserDto creator;
    private List<TaskTemplateDto> taskTemplates;
    private String name;
    private String description;

    public HomeworkTemplateDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private HomeworkTemplateDto(long id, LocalDateTime created, LocalDateTime updated,
                                UserDto creator, List<TaskTemplateDto> taskTemplates,
                                String name, String description) {
        super(id, created, updated);
        this.creator = creator;
        this.taskTemplates = taskTemplates;
        this.name = name;
        this.description = description;
    }

    public static HomeworkTemplateDto of(HomeworkTemplate template) {
        return new HomeworkTemplateDto(template.getId(), template.getCreated(),
                template.getUpdated(), UserDto.of(template.getCreator()),
                template.getTaskTemplates().stream().map(TaskTemplateDto::of).collect(Collectors.toList()),
                template.getName(), template.getDescription());
    }
}
