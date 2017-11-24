package org.smirnowku.hwsc.server.model.dto;

import org.smirnowku.hwsc.server.exception.IllegalArgumentException;

public class TaskTemplateDto {

    private String name;
    private String description;

    public String getName() {
        if (PropertyValidator.isEmpty(name))
            throw new IllegalArgumentException("Name cannot be empty");
        return name;
    }

    public String getDescription() {
        if (PropertyValidator.isEmpty(description))
            throw new IllegalArgumentException("Description cannot be empty");
        return description;
    }

    @Override
    public String toString() {
        return "TaskTemplateDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
