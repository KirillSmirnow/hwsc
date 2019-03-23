package hwsc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class HomeworkProgressDto {

    private AssignmentDto assignment;
    private UserDto checker;
}
