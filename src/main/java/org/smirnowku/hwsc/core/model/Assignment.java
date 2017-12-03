package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.dto.AssignmentDto;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.persistence.*;

@Entity
public class Assignment extends BaseEntity {

    public enum Status {
        TODO,
        SUBMITTED,
        CHECKING,
        COMPLETED
    }

    @ManyToOne(optional = false)
    private User student;

    @ManyToOne(optional = false)
    private Homework homework;

    @OneToOne(optional = false)
    private HomeworkSolution homeworkSolution;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer score;

    public Assignment() {
    }

    public Assignment(User student, Homework homework, HomeworkSolution homeworkSolution) {
        this.student = student;
        this.homework = homework;
        this.homeworkSolution = homeworkSolution;
        this.status = Status.TODO;
        this.score = -1;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setScore(Integer score) {
        validateScore(score);
        this.score = score;
    }

    public User getStudent() {
        return student;
    }

    public Homework getHomework() {
        return homework;
    }

    public HomeworkSolution getHomeworkSolution() {
        return homeworkSolution;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getScore() {
        return score;
    }

    public AssignmentDto toDto() {
        return new AssignmentDto(getId(), getCreated(), getUpdated(), student.toDto(),
                homework.toDto(), homeworkSolution.toDto(), status, score);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "student=" + student +
                ", homework=" + homework +
                ", homeworkSolution=" + homeworkSolution +
                ", status=" + status +
                ", score=" + score +
                '}';
    }

    private void validateScore(Integer score) {
        if (PropertyValidator.isEmpty(score)) throw new IllegalArgumentException("Score cannot be empty");
        if (score < 0) throw new IllegalArgumentException("Score cannot be negative");
    }
}
