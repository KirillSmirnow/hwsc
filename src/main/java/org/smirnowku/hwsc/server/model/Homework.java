package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

@Entity
public class Homework extends BaseEntity {

    @OneToOne
    private Classroom classroom;

    @OneToMany
    private List<Task> tasks;

    private String name;
    private String description;
    private Date deadline;
    private Integer subgroupSize;

    public Homework() {
    }

    public Homework(HomeworkTemplate template, Classroom classroom, List<Task> tasks, Date deadline, Integer subgroupSize) {
        this.classroom = classroom;
        this.tasks = tasks;
        this.name = template.getName();
        this.description = template.getDescription();
        this.deadline = deadline;
        this.subgroupSize = subgroupSize;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Integer getSubgroupSize() {
        return subgroupSize;
    }
}
