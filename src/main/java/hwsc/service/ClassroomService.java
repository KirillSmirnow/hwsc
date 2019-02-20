package hwsc.service;

import hwsc.dto.ClassroomDto;
import hwsc.dto.HomeworkDto;
import hwsc.model.Classroom;

import java.util.List;

public interface ClassroomService {

    void create(String teacherUsername, ClassroomDto dto);

    void addMembers(String username, long id, List<String> studentsUsernames, List<String> teachersUsernames);

    void edit(String username, long id, ClassroomDto dto);

    ClassroomDto get(String username, long id);

    List<ClassroomDto> getClassroomsAsStudent(String studentUsername);

    List<ClassroomDto> getClassroomsAsTeacher(String teacherUsername);

    List<HomeworkDto> getHomeworks(String username, long id);

    Classroom getEntity(String username, long id);
}
