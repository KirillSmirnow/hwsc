package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class HomeworkTemplate extends BaseEntity {

    @ManyToOne
    private User creator;

    @OneToMany
    private List<TaskTemplate> taskTemplates;

    private String name;
    private String description;

    public HomeworkTemplate() {
    }

    public HomeworkTemplate(User creator, String name, String description) {
        this.creator = creator;
        setName(name);
        setDescription(description);
    }

    public void setTaskTemplates(List<TaskTemplate> taskTemplates) {
        this.taskTemplates = taskTemplates;
    }

    public void setName(String name) {
        if (PropertyValidator.isEmpty(name))
            throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public List<TaskTemplate> getTaskTemplates() {
        return taskTemplates;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public HomeworkTemplateDto toDto() {
        return new HomeworkTemplateDto(getId(), getCreated(), getUpdated(), creator.toDto(),
                taskTemplates.stream().map(TaskTemplate::toDto).collect(Collectors.toList()), name, description);
    }

    @Override
    public String toString() {
        return "HomeworkTemplate{" +
                "creator=" + creator +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
