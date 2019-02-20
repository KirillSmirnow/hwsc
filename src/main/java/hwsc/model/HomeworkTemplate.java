package hwsc.model;

import hwsc.HwscException;
import hwsc.dto.HomeworkTemplateDto;
import hwsc.util.PropertyValidator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class HomeworkTemplate extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    @ManyToOne(optional = false)
    private User creator;

    @OneToMany
    private List<TaskTemplate> taskTemplates;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = true, length = MAX_DESCRIPTION_LENGTH)
    private String description;

    public HomeworkTemplate() {
    }

    public HomeworkTemplate(User creator, String name, String description) {
        this.creator = creator;
        setName(name);
        setDescription(description);
    }

    public User getCreator() {
        return creator;
    }

    public List<TaskTemplate> getTaskTemplates() {
        return taskTemplates;
    }

    public void setTaskTemplates(List<TaskTemplate> taskTemplates) {
        this.taskTemplates = taskTemplates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
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

    private void validateName(String name) {
        if (PropertyValidator.isEmpty(name)) throw new IllegalArgumentException("Name cannot be empty");
        if (name.length() > MAX_NAME_LENGTH)
            throw new HwscException(String.format("Name is too long (max length is %d)", MAX_NAME_LENGTH),
                    String.format("Name is too long (max length is %d, current length is %d)", MAX_NAME_LENGTH, name.length()));
    }

    private void validateDescription(String description) {
        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH)
            throw new HwscException(String.format("Description is too long (max length is %d)", MAX_DESCRIPTION_LENGTH),
                    String.format("Description is too long (max length is %d, current length is %d)", MAX_DESCRIPTION_LENGTH, description.length()));
    }
}
