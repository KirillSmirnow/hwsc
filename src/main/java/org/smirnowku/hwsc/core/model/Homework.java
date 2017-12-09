package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
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

    private LocalDateTime deadline;
    private Integer subgroupSize;

    public Homework() {
    }

    public Homework(HomeworkTemplate template, Classroom classroom, List<Task> tasks,
                    LocalDateTime deadline, Integer subgroupSize) {
        validateTasks(tasks);
        validateSubgroupSize(subgroupSize, classroom);
        this.classroom = classroom;
        this.tasks = tasks;
        this.name = template.getName();
        this.description = template.getDescription();
        this.deadline = deadline;
        this.subgroupSize = subgroupSize;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public Integer getSubgroupSize() {
        return subgroupSize;
    }

    public HomeworkDto toDto() {
        return new HomeworkDto(getId(), getCreated(), getUpdated(), classroom.toDto(),
                tasks.stream().map(Task::toDto).collect(Collectors.toList()), name, description, deadline, subgroupSize);
    }

    @Override
    public String toString() {
        return "Homework{" +
                "classroom=" + classroom +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", subgroupSize=" + subgroupSize +
                '}';
    }

    private void validateTasks(List<Task> tasks) {
        if (PropertyValidator.isEmpty(tasks))
            throw new IllegalArgumentException("Homework must contain at least one task");
    }

    private void validateSubgroupSize(Integer subgroupSize, Classroom classroom) {
        if (PropertyValidator.isEmpty(subgroupSize))
            throw new IllegalArgumentException("Subgroup size cannot be empty");
        int studentsQty = classroom.getStudents().size();
        if (subgroupSize < 2 || subgroupSize > studentsQty)
            throw new IllegalArgumentException(String.format("Subgroup size must be between 2 and %d", studentsQty),
                    String.format("Subgroup size must be between 2 and %d, actual = %d", studentsQty, subgroupSize));
    }
}
