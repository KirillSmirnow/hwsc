package hwsc.dto;

import hwsc.model.Check;

import java.util.Date;

public class CheckDto extends BaseDto {

    private UserDto checker;
    private AssignmentDto assignment;
    private Check.Status status;

    public CheckDto() {
    }

    public CheckDto(long id, Date created, Date updated, UserDto checker,
                    AssignmentDto assignment, Check.Status status) {
        super(id, created, updated);
        this.checker = checker;
        this.assignment = assignment;
        this.status = status;
    }

    public UserDto getChecker() {
        return checker;
    }

    public AssignmentDto getAssignment() {
        return assignment;
    }

    public Check.Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "CheckDto{" +
                "checker=" + checker +
                ", assignment=" + assignment +
                ", status=" + status +
                '}';
    }
}
