package org.smirnowku.hwsc.dto;

import java.util.Date;
import java.util.List;

public class HomeworkTemplateDto extends BaseDto {

    private UserDto creator;
    private List<TaskTemplateDto> taskTemplates;
    private String name;
    private String description;

    public HomeworkTemplateDto() {
    }

    public HomeworkTemplateDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public HomeworkTemplateDto(long id, Date created, Date updated, UserDto creator,
                               List<TaskTemplateDto> taskTemplates, String name, String description) {
        super(id, created, updated);
        this.creator = creator;
        this.taskTemplates = taskTemplates;
        this.name = name;
        this.description = description;
    }

    public UserDto getCreator() {
        return creator;
    }

    public List<TaskTemplateDto> getTaskTemplates() {
        return taskTemplates;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "HomeworkTemplateDto{" +
                "creator=" + creator +
                ", taskTemplates=" + taskTemplates +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
