package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;

@Entity
public class Task extends BaseEntity {

    private String name;
    private String description;

    public Task() {
    }

    public Task(TaskTemplate template) {
        this.name = template.getName();
        this.description = template.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
