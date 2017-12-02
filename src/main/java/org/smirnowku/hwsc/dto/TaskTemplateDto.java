package org.smirnowku.hwsc.dto;

import java.util.Date;

public class TaskTemplateDto extends BaseDto {

    private String name;
    private String description;

    public TaskTemplateDto() {
    }

    public TaskTemplateDto(long id, Date created, Date updated, String name, String description) {
        super(id, created, updated);
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
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
        return "TaskTemplateDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
