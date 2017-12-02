package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.dto.TaskTemplateDto;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.persistence.Entity;

@Entity
public class TaskTemplate extends BaseEntity {

    private String name;
    private String description;

    public TaskTemplate() {
    }

    public TaskTemplate(String name, String description) {
        if (PropertyValidator.isEmpty(name))
            throw new IllegalArgumentException("Name cannot be empty");
        if (PropertyValidator.isEmpty(description))
            throw new IllegalArgumentException("Description cannot be empty");
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskTemplateDto toDto() {
        return new TaskTemplateDto(getId(), getCreated(), getUpdated(), name, description);
    }

    @Override
    public String toString() {
        return "TaskTemplate{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
