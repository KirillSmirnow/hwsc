package org.smirnowku.hwsc.dto;

import java.util.Date;
import java.util.Set;

public class ClassroomDto extends BaseDto {

    private Set<UserDto> teachers;
    private Set<UserDto> students;
    private String name;
    private String description;

    public ClassroomDto() {
    }

    public ClassroomDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ClassroomDto(long id, Date created, Date updated, Set<UserDto> teachers, Set<UserDto> students,
                        String name, String description) {
        super(id, created, updated);
        this.teachers = teachers;
        this.students = students;
        this.name = name;
        this.description = description;
    }

    public Set<UserDto> getTeachers() {
        return teachers;
    }

    public Set<UserDto> getStudents() {
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
        return "ClassroomDto{" +
                "teachers=" + teachers +
                ", students=" + students +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
