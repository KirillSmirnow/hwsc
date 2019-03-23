package hwsc.dto;

import hwsc.model.Classroom;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(callSuper = true)
public class ClassroomDto extends BaseDto {

    private Set<UserDto> teachers;
    private Set<UserDto> students;
    private String name;
    private String description;

    public ClassroomDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private ClassroomDto(long id, LocalDateTime created, LocalDateTime updated,
                         Set<UserDto> teachers, Set<UserDto> students,
                         String name, String description) {
        super(id, created, updated);
        this.teachers = teachers;
        this.students = students;
        this.name = name;
        this.description = description;
    }

    public static ClassroomDto of(Classroom classroom) {
        return new ClassroomDto(classroom.getId(), classroom.getCreated(), classroom.getUpdated(),
                classroom.getTeachers().stream().map(UserDto::of).collect(Collectors.toSet()),
                classroom.getStudents().stream().map(UserDto::of).collect(Collectors.toSet()),
                classroom.getName(), classroom.getDescription());
    }
}
