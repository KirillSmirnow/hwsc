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
    private Progress homeworkToCheck;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getChecker() {
        return checker;
    }

    public Progress getHomeworkToCheck() {
        return homeworkToCheck;
    }

    public Status getStatus() {
        return status;
    }
}
