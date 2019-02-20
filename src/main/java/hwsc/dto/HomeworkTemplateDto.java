package hwsc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HomeworkTemplateDto extends BaseDto {

    private UserDto creator;
    private List<TaskTemplateDto> taskTemplates;
    private String name;
    private String description;

    public HomeworkTemplateDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public HomeworkTemplateDto(long id, Date created, Date updated,
                               UserDto creator, List<TaskTemplateDto> taskTemplates,
                               String name, String description) {
        super(id, created, updated);
        this.creator = creator;
        this.taskTemplates = taskTemplates;
        this.name = name;
        this.description = description;
    }
}
