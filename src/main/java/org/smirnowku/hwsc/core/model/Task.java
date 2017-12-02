package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.dto.TaskDto;

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

    public TaskDto toDto() {
        return new TaskDto(getId(), getCreated(), getUpdated(), name, description);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
