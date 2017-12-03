package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.dto.TaskTemplateDto;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TaskTemplate extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;

    public TaskTemplate() {
    }

    public TaskTemplate(String name, String description) {
        validateName(name);
        validateDescription(description);
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

    private void validateName(String name) {
        if (PropertyValidator.isEmpty(name)) throw new IllegalArgumentException("Name cannot be empty");
        if (name.length() > MAX_NAME_LENGTH)
            throw new IllegalArgumentException(String.format("Name is too long (max length is %d)", MAX_NAME_LENGTH),
                    String.format("Name is too long (max length is %d, current length is %d)", MAX_NAME_LENGTH, name.length()));
    }

    private void validateDescription(String description) {
        if (PropertyValidator.isEmpty(description)) throw new IllegalArgumentException("Description cannot be empty");
        if (description.length() > MAX_DESCRIPTION_LENGTH)
            throw new IllegalArgumentException(String.format("Description is too long (max length is %d)", MAX_DESCRIPTION_LENGTH),
                    String.format("Description is too long (max length is %d, current length is %d)", MAX_DESCRIPTION_LENGTH, description.length()));
    }
}
