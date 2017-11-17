package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class Progress extends BaseEntity {

    public enum Status {
        TODO,
        SOLVED,
        ASSIGNED,
        COMPLETED
    }

    @ManyToOne
    private User student;

    @ManyToOne
    private HomeworkSolution homeworkSolution;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer result;

    public Progress() {
    }

    public Progress(User student, HomeworkSolution homeworkSolution) {
        this.student = student;
        this.homeworkSolution = homeworkSolution;
        this.status = Status.TODO;
        this.result = 0;
    }

    public HomeworkSolution homeworkSolution() {
        return homeworkSolution;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public User getStudent() {
        return student;
    }

    public Homework getHomework() {
        return homeworkSolution.getHomework();
    }

    public Status getStatus() {
        return status;
    }

    public Integer getResult() {
        return result;
    }
}
