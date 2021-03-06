package hwsc.model;

import hwsc.HwscException;
import hwsc.util.PropertyValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "homeworks")
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class Homework extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    @ManyToOne(optional = false)
    private Classroom classroom;

    @OneToMany
    private List<Task> tasks;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = true, length = MAX_DESCRIPTION_LENGTH)
    private String description;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime deadline;

    private Integer subgroupSize;

    public Homework(HomeworkTemplate template, Classroom classroom, List<Task> tasks,
                    LocalDateTime deadline, Integer subgroupSize) {
        validateTasks(tasks);
        validateSubgroupSize(subgroupSize, classroom);
        this.classroom = classroom;
        this.tasks = tasks;
        this.name = template.getName();
        this.description = template.getDescription();
        this.status = Status.ACTIVE;
        this.deadline = deadline;
        this.subgroupSize = subgroupSize;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private void validateTasks(List<Task> tasks) {
        if (PropertyValidator.isEmpty(tasks))
            throw new HwscException("Homework must contain at least one task");
    }

    private void validateSubgroupSize(Integer subgroupSize, Classroom classroom) {
        if (PropertyValidator.isEmpty(subgroupSize))
            throw new HwscException("Subgroup size cannot be empty");
        int studentsQty = classroom.getStudents().size();
        if (subgroupSize < 2 || subgroupSize > studentsQty)
            throw new HwscException(String.format("Subgroup size must be between 2 and %d", studentsQty),
                    String.format("Subgroup size must be between 2 and %d, actual = %d", studentsQty, subgroupSize));
    }

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
