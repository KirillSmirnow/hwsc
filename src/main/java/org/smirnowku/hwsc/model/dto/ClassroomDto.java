package org.smirnowku.hwsc.model.dto;

import org.smirnowku.hwsc.exception.IllegalArgumentException;

public class ClassroomDto {

    private String name;
    private String description;

    public String getName() {
        if (PropertyValidator.isEmpty(name))
            throw new IllegalArgumentException("Name cannot be empty");
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ClassroomDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}