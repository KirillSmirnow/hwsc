package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.Classroom;
import org.smirnowku.hwsc.server.model.Homework;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.repository.ClassroomRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class ClassroomService {

    @Resource
    private UserService userService;

    @Resource
    private ClassroomRepository classroomRepository;

    public Classroom getClassroom(long id) {
        return classroomRepository.findOne(id);
    }

    public List<User> getTeachers(long id) {
        return getClassroom(id).teachers();
    }

    public List<User> getStudents(long id) {
        return getClassroom(id).students();
    }

    public List<Homework> getHomeworks(long id) {
        return getClassroom(id).homeworks();
    }

    public List<Classroom> getClassroomsAsTeacher(long teacherId) {
        User teacher = userService.getUser(teacherId);
        return classroomRepository.findAllByTeachers(teacher);
    }

    public List<Classroom> getClassroomsAsStudent(long studentId) {
        User student = userService.getUser(studentId);
        return classroomRepository.findAllByStudents(student);
    }

    public void createClassroom(long teacherId, String name) {
        User teacher = userService.getUser(teacherId);
        Classroom classroom = new Classroom(teacher, name);
        classroomRepository.save(classroom);
    }

    public void addMembers(long classroomId, long[] studentsIds, long[] teachersIds) {
        Classroom classroom = getClassroom(classroomId);
        if (studentsIds != null)
            Arrays.stream(studentsIds).forEach(studentId -> {
                try {
                    User student = userService.getUser(studentId);
                    addStudentToClassroom(classroom, student);
                } catch (Exception e) {
                }
            });
        if (teachersIds != null)
            Arrays.stream(teachersIds).forEach(teacherId -> {
                try {
                    User teacher = userService.getUser(teacherId);
                    addTeacherToClassroom(classroom, teacher);
                } catch (Exception e) {
                }
            });
        classroomRepository.save(classroom);
    }

    private void addStudentToClassroom(Classroom classroom, User student) {
        if (!classroom.students().contains(student))
            classroom.students().add(student);
    }

    private void addTeacherToClassroom(Classroom classroom, User teacher) {
        if (!classroom.teachers().contains(teacher))
            classroom.teachers().add(teacher);
    }
}
