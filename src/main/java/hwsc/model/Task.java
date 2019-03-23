package hwsc.model;

import hwsc.HwscException;
import hwsc.util.PropertyValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class Task extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 100000;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;

    public Task(TaskTemplate template) {
        validateDescription(template.getDescription(), template.getName());
        this.name = template.getName();
        this.description = template.getDescription();
    }

    private void validateDescription(String description, String name) {
        if (PropertyValidator.isEmpty(description))
            throw new HwscException(String.format("Task %s has no description", name));
        if (description.length() > MAX_DESCRIPTION_LENGTH)
            throw new HwscException(String.format("Description is too long (max length is %d)", MAX_DESCRIPTION_LENGTH),
                    String.format("Description is too long (max length is %d, current length is %d)",
                            MAX_DESCRIPTION_LENGTH, description.length()));
    }
}
