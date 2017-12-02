package org.smirnowku.hwsc.dto;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;

public class ClassroomDto {

    private String name;
    private String description;

    public ClassroomDto() {
    }

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
