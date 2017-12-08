package org.smirnowku.hwsc.core.service;

import org.smirnowku.hwsc.core.model.Classroom;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.dto.HomeworkDto;

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
