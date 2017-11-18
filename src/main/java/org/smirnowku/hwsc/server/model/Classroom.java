package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Classroom extends BaseEntity {

    @ManyToMany
    private List<User> teachers;

    @ManyToMany
    private List<User> students;

    private String name;
    private String description;

    public Classroom() {
    }

    public Classroom(User teacher, String name, String description) {
        this.teachers = new ArrayList<>();
        this.students = new ArrayList<>();
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

    public List<User> getTeachers() {
        return teachers;
    }

    public List<User> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
