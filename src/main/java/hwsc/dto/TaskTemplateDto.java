package hwsc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TaskTemplateDto extends BaseDto {

    private String name;
    private String description;

    public TaskTemplateDto(String name) {
        this.name = name;
    }

    public TaskTemplateDto(long id, Date created, Date updated, String name, String description) {
        super(id, created, updated);
        this.name = name;
        this.description = description;
    }
}
