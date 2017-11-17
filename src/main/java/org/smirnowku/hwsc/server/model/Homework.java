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
    private Integer subgroupSize;

    public Homework() {
    }

    public Homework(String name, Date deadline, Integer subgroupSize) {
        this.name = name;
        this.deadline = deadline;
        this.subgroupSize = subgroupSize;
    }

    public String getName() {
        return name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Integer getSubgroupSize() {
        return subgroupSize;
    }
}
