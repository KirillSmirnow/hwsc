package hwsc.model;

import hwsc.HwscException;
import hwsc.util.PropertyValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "task_templates")
@Getter
@NoArgsConstructor
public class TaskTemplate extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 100000;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = true, length = MAX_DESCRIPTION_LENGTH)
    private String description;

    public TaskTemplate(String name, String description) {
        validateName(name);
        validateDescription(description);
        this.name = name;
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
