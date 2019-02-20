package hwsc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TaskSolutionDto extends BaseDto {

    private String link;

    public TaskSolutionDto(long id, Date created, Date updated, String link) {
        super(id, created, updated);
        this.link = link;
    }
}
