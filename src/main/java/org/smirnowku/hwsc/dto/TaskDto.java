package org.smirnowku.hwsc.dto;

import java.util.Date;

public class TaskDto extends BaseDto {

    private String name;
    private String description;

    public TaskDto() {
    }

    public TaskDto(long id, Date created, Date updated, String name, String description) {
        super(id, created, updated);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
