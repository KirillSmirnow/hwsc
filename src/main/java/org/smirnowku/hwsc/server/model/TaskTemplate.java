package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;

@Entity
public class TaskTemplate extends BaseEntity {

    private String name;
    private String description;

    public TaskTemplate() {
    }

    public TaskTemplate(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
