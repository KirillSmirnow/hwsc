package org.smirnowku.hwsc.dto;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;

import java.util.List;

public class HomeworkTemplateDto {

    private String name;
    private String description;
    private List<TaskTemplateDto> taskTemplates;

    public String getName() {
        if (PropertyValidator.isEmpty(name))
            throw new IllegalArgumentException("Name cannot be empty");
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<TaskTemplateDto> getTaskTemplates() {
        if (PropertyValidator.isEmpty(taskTemplates))
            throw new IllegalArgumentException("Task templates cannot be empty");
        return taskTemplates;
    }

    @Override
    public String toString() {
        return "HomeworkTemplateDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskTemplates=" + taskTemplates +
                '}';
    }
}
