package org.smirnowku.hwsc.model;

import javax.persistence.*;

@Entity
public class Assignment extends BaseEntity {

    public enum Status {
        TODO,
        SUBMITTED,
        CHECKING,
        COMPLETED
    }

    @ManyToOne
    private User student;

    @ManyToOne
    private Homework homework;

    @OneToOne
    private HomeworkSolution homeworkSolution;

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
}
