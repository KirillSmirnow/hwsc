package org.smirnowku.hwsc.dto;

public class HomeworkProgressDto {

    private AssignmentDto assignment;
    private UserDto checker;

    public HomeworkProgressDto(AssignmentDto assignment, UserDto checker) {
        this.assignment = assignment;
        this.checker = checker;
    }

    public AssignmentDto getAssignment() {
        return assignment;
    }

    public UserDto getChecker() {
        return checker;
    }

    @Override
    public String toString() {
        return "HomeworkProgressDto{" +
                "assignment=" + assignment +
                ", checker=" + checker +
                '}';
    }
}
