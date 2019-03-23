package hwsc.dto;

import hwsc.model.Check;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class CheckDto extends BaseDto {

    private UserDto checker;
    private AssignmentDto assignment;
    private Check.Status status;

    private CheckDto(long id, LocalDateTime created, LocalDateTime updated,
                     UserDto checker, AssignmentDto assignment, Check.Status status) {
        super(id, created, updated);
        this.checker = checker;
        this.assignment = assignment;
        this.status = status;
    }

    public static CheckDto of(Check check) {
        return new CheckDto(check.getId(), check.getCreated(), check.getUpdated(), UserDto.of(check.getChecker()),
                AssignmentDto.of(check.getAssignment()), check.getStatus());
    }
}
