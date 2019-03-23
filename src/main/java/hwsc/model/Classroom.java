package hwsc.model;

import hwsc.HwscException;
import hwsc.util.PropertyValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classrooms")
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class Classroom extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    @ManyToMany
    private Set<User> teachers;

    @ManyToMany
    private Set<User> students;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = true, length = MAX_DESCRIPTION_LENGTH)
    private String description;

    public Classroom(User teacher, String name, String description) {
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
        this.teachers.add(teacher);
        setName(name);
        setDescription(description);
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
