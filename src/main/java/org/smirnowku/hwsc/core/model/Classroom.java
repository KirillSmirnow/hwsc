package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Classroom extends BaseEntity {

    @ManyToMany
    private Set<User> teachers;

    @ManyToMany
    private Set<User> students;

    private String name;
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
        if (PropertyValidator.isEmpty(name))
            throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public void setDescription(String description) {
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
}
