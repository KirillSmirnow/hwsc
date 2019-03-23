package hwsc.model;

import hwsc.HwscException;
import hwsc.util.PropertyValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "homework_templates")
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
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

    public HomeworkTemplate(User creator, String name, String description) {
        this.creator = creator;
        setName(name);
        setDescription(description);
    }

    public void setTaskTemplates(List<TaskTemplate> taskTemplates) {
        this.taskTemplates = taskTemplates;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    private void validateName(String name) {
        if (PropertyValidator.isEmpty(name)) throw new HwscException("Name cannot be empty");
        if (name.length() > MAX_NAME_LENGTH)
            throw new HwscException(String.format("Name is too long (max length is %d)", MAX_NAME_LENGTH),
                    String.format("Name is too long (max length is %d, current length is %d)",
                            MAX_NAME_LENGTH, name.length()));
    }

    private void validateDescription(String description) {
        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH)
            throw new HwscException(String.format("Description is too long (max length is %d)", MAX_DESCRIPTION_LENGTH),
                    String.format("Description is too long (max length is %d, current length is %d)",
                            MAX_DESCRIPTION_LENGTH, description.length()));
    }
}
