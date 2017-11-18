package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.Classroom;
import org.smirnowku.hwsc.server.model.Homework;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.model.dto.ClassroomDto;
import org.smirnowku.hwsc.server.repository.ClassroomRepository;
import org.smirnowku.hwsc.server.repository.HomeworkRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class ClassroomService {

    private interface MemberAdder {
        void addMemberToClassroom(Classroom classroom, User user);
    }

    @Resource
    private UserService userService;

    @Resource
    private ClassroomRepository classroomRepository;

    @Resource
    private HomeworkRepository homeworkRepository;

    public void create(long teacherId, ClassroomDto dto) {
        User teacher = userService.get(teacherId);
        Classroom classroom = new Classroom(teacher, dto.name, dto.description);
        classroomRepository.save(classroom);
    }

    public void addMembers(long userId, long id, long[] studentsIds, long[] teachersIds) {
        Classroom classroom = get(userId, id);
        addMembersToClassroom(classroom, studentsIds, this::addStudentToClassroom);
        addMembersToClassroom(classroom, teachersIds, this::addTeacherToClassroom);
        classroomRepository.save(classroom);
    }

    public void edit(long userId, long id, ClassroomDto dto) {
        Classroom classroom = get(userId, id);
        classroom.setName(dto.name);
        classroom.setDescription(dto.description);
        classroomRepository.save(classroom);
    }

    public List<Classroom> getClassroomsAsStudent(long studentId) {
        User student = userService.get(studentId);
        return classroomRepository.findAllByStudents(student);
    }

    public List<Classroom> getClassroomsAsTeacher(long teacherId) {
        User teacher = userService.get(teacherId);
        return classroomRepository.findAllByTeachers(teacher);
    }

    public Classroom get(long userId, long id) {
        return classroomRepository.findOne(id);
    }

    public List<Homework> getHomeworks(long userId, long id) {
        Classroom classroom = get(userId, id);
        return homeworkRepository.findAllByClassroom(classroom);
    }

    private void addMembersToClassroom(Classroom classroom, long[] usersIds, MemberAdder memberAdder) {
        if (usersIds == null) return;
        Arrays.stream(usersIds).forEach(userId -> {
            try {
                User user = userService.get(userId);
                memberAdder.addMemberToClassroom(classroom, user);
            } catch (Exception ignored) {
            }
        });
    }

    private void addStudentToClassroom(Classroom classroom, User student) {
        if (!classroom.getStudents().contains(student))
            classroom.getStudents().add(student);
    }

    private void addTeacherToClassroom(Classroom classroom, User teacher) {
        if (!classroom.getTeachers().contains(teacher))
            classroom.getTeachers().add(teacher);
    }
}
