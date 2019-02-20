package hwsc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HomeworkProgressDto {

    private AssignmentDto assignment;
    private UserDto checker;

    public HomeworkProgressDto(AssignmentDto assignment, UserDto checker) {
        this.assignment = assignment;
        this.checker = checker;
    }
}
