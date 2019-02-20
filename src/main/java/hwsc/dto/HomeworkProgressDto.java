package hwsc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HomeworkProgressDto {

    private AssignmentDto assignment;
    private UserDto checker;
}
