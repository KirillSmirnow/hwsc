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

    @ManyToMany
    private List<Homework> homeworks;

    private String name;

    public Classroom() {
    }

    public Classroom(User teacher, String name) {
        this.teachers = new ArrayList<>();
        this.teachers.add(teacher);
        this.name = name;
    }

    public List<User> teachers() {
        return teachers;
    }

    public List<User> students() {
        return students;
    }

    public List<Homework> homeworks() {
        return homeworks;
    }

    public String getName() {
        return name;
    }
}
