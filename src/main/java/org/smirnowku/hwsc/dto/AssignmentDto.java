package org.smirnowku.hwsc.dto;

import org.smirnowku.hwsc.core.model.Assignment;

import java.util.Date;

public class AssignmentDto extends BaseDto {

    private UserDto student;
    private HomeworkDto homework;
    private HomeworkSolutionDto homeworkSolution;
    private Assignment.Status status;
    private Integer score;

    public AssignmentDto() {
    }

    public AssignmentDto(long id, Date created, Date updated, UserDto student, HomeworkDto homework,
                         HomeworkSolutionDto homeworkSolution, Assignment.Status status, Integer score) {
        super(id, created, updated);
        this.student = student;
        this.homework = homework;
        this.homeworkSolution = homeworkSolution;
        this.status = status;
        this.score = score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public UserDto getStudent() {
        return student;
    }

    public HomeworkDto getHomework() {
        return homework;
    }

    public HomeworkSolutionDto getHomeworkSolution() {
        return homeworkSolution;
    }

    public Assignment.Status getStatus() {
        return status;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "AssignmentDto{" +
                "student=" + student +
                ", homework=" + homework +
                ", homeworkSolution=" + homeworkSolution +
                ", status=" + status +
                ", score=" + score +
                '}';
    }
}
