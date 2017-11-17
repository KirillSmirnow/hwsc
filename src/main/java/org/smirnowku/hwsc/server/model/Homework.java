package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Homework extends BaseEntity {

    @ManyToMany
    private List<Task> tasks;

    private String name;
    private Date deadline;

    public Homework() {
    }

    public Homework(String name, Date deadline) {
        this.name = name;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public Date getDeadline() {
        return deadline;
    }
}
