package org.smirnowku.hwsc.core.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Classroom extends BaseEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> teachers;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> students;

    private String name;
    private String description;

    public Classroom() {
    }

    public Classroom(User teacher, String name, String description) {
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
        this.teachers.add(teacher);
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
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

    @Override
    public String toString() {
        return "Classroom{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
