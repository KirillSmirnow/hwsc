package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
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

    public Classroom() {
    }

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

    public Set<User> getTeachers() {
        return teachers;
    }

    public Set<User> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ClassroomDto toDto() {
        return new ClassroomDto(getId(), getCreated(), getUpdated(),
                teachers.stream().map(User::toDto).collect(Collectors.toSet()),
                students.stream().map(User::toDto).collect(Collectors.toSet()),
                name, description);
    }

    @Override
    public String toString() {
        return "Classroom{" +
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
        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH)
            throw new IllegalArgumentException(String.format("Description is too long (max length is %d)", MAX_DESCRIPTION_LENGTH),
                    String.format("Description is too long (max length is %d, current length is %d)", MAX_DESCRIPTION_LENGTH, description.length()));
    }
}
