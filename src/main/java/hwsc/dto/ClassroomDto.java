package hwsc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ClassroomDto extends BaseDto {

    private Set<UserDto> teachers;
    private Set<UserDto> students;
    private String name;
    private String description;

    public ClassroomDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ClassroomDto(long id, Date created, Date updated, Set<UserDto> teachers,
                        Set<UserDto> students, String name, String description) {
        super(id, created, updated);
        this.teachers = teachers;
        this.students = students;
        this.name = name;
        this.description = description;
    }
}
