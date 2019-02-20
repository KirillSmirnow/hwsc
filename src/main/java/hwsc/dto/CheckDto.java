package hwsc.dto;

import hwsc.model.Check;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CheckDto extends BaseDto {

    private UserDto checker;
    private AssignmentDto assignment;
    private Check.Status status;

    public CheckDto(long id, Date created, Date updated, UserDto checker,
                    AssignmentDto assignment, Check.Status status) {
        super(id, created, updated);
        this.checker = checker;
        this.assignment = assignment;
        this.status = status;
    }
}
