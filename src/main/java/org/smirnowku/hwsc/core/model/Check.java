package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.dto.CheckDto;

import javax.persistence.*;

@Entity
@Table(name = "hwsc_check")
public class Check extends BaseEntity {

    public enum Status {
        PENDING,
        CHECKED
    }

    @ManyToOne(optional = false)
    private User checker;

    @OneToOne(optional = false)
    private Assignment assignment;

    @Column(nullable = false, length = 10)
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

    public CheckDto toDto() {
        return new CheckDto(getId(), getCreated(), getUpdated(), checker.toDto(), assignment.toDto(), status);
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
