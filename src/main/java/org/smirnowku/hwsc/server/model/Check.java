package org.smirnowku.hwsc.server.model;

import javax.persistence.*;

@Entity
@Table(name = "hwsc_check")
public class Check extends BaseEntity {

    public enum Status {
        PENDING,
        CHECKED
    }

    @ManyToOne
    private User checker;

    @OneToOne
    private Assignment assignment;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Check() {
    }

    public Check(User checker, Assignment assignment) {
        this.checker = checker;
        this.assignment = assignment;
        this.status = Status.PENDING;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getChecker() {
        return checker;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Check{" +
                "checker=" + checker +
                ", assignment=" + assignment +
                ", status=" + status +
                '}';
    }
}
