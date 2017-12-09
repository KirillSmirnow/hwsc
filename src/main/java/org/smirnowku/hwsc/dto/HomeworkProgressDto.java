package org.smirnowku.hwsc.dto;

import org.smirnowku.hwsc.core.model.Assignment;

public class HomeworkProgressDto {

    private UserDto student;
    private Assignment.Status status;
    private UserDto checker;
    private Integer score;

    public HomeworkProgressDto(UserDto student, Assignment.Status status, UserDto checker, Integer score) {
        this.student = student;
        this.status = status;
        this.checker = checker;
        this.score = score;
    }

    public UserDto getStudent() {
        return student;
    }

    public Assignment.Status getStatus() {
        return status;
    }

    public UserDto getChecker() {
        return checker;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "HomeworkProgressDto{" +
                "student=" + student +
                ", status=" + status +
                ", checker=" + checker +
                ", score=" + score +
                '}';
    }
}
