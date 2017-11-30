package org.smirnowku.hwsc.service;

import org.smirnowku.hwsc.exception.ForbiddenException;
import org.smirnowku.hwsc.exception.HwscException;
import org.smirnowku.hwsc.exception.NotFoundException;
import org.smirnowku.hwsc.model.Classroom;
import org.smirnowku.hwsc.model.Homework;
import org.smirnowku.hwsc.model.User;
import org.smirnowku.hwsc.model.dto.ClassroomDto;
import org.smirnowku.hwsc.repository.ClassroomRepository;
import org.smirnowku.hwsc.repository.HomeworkRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public void create(String teacherUsername, ClassroomDto dto) {
        User teacher = userService.get(teacherUsername);
        Classroom classroom = new Classroom(teacher, dto.getName(), dto.getDescription());
        classroomRepository.save(classroom);
    }

    public void addMembers(String username, long id, List<String> studentsUsernames, List<String> teachersUsernames) {
        Classroom classroom = get(username, id);
        User user = userService.get(username);
        authorizeUpdate(classroom, user);
        addMembersToClassroom(classroom, studentsUsernames, this::addStudentToClassroom);
        addMembersToClassroom(classroom, teachersUsernames, this::addTeacherToClassroom);
        classroomRepository.save(classroom);
    }

    public void edit(String username, long id, ClassroomDto dto) {
        Classroom classroom = get(username, id);
        User user = userService.get(username);
        authorizeUpdate(classroom, user);
        classroom.setName(dto.getName());
        classroom.setDescription(dto.getDescription());
        classroomRepository.save(classroom);
    }

    public List<Classroom> getClassroomsAsStudent(String studentUsername) {
        User student = userService.get(studentUsername);
        return classroomRepository.findAllByStudents(student);
    }

    public List<Classroom> getClassroomsAsTeacher(String teacherUsername) {
        User teacher = userService.get(teacherUsername);
        return classroomRepository.findAllByTeachers(teacher);
    }

    public Classroom get(String username, long id) {
        Classroom classroom = classroomRepository.findOne(id);
        if (classroom == null) throw new NotFoundException("Classroom not found");
        User user = userService.get(username);
        authorizeRead(classroom, user);
        return classroom;
    }

    public List<Homework> getHomeworks(String username, long id) {
        Classroom classroom = get(username, id);
        return homeworkRepository.findAllByClassroom(classroom);
    }

    private void addMembersToClassroom(Classroom classroom, List<String> usernames, MemberAdder memberAdder) {
        if (usernames == null) return;
        usernames.forEach(username -> {
            User user;
            try {
                user = userService.get(username);
            } catch (HwscException e) {
                return;
            }
            memberAdder.addMemberToClassroom(classroom, user);
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

    private void authorizeUpdate(Classroom classroom, User user) {
        if (!classroom.getTeachers().contains(user))
            throw new ForbiddenException("You are not allowed to update this classroom: you are not a teacher");
    }

    private void authorizeRead(Classroom classroom, User user) {
        if (!classroom.getTeachers().contains(user) && !classroom.getStudents().contains(user))
            throw new ForbiddenException("You are not allowed to access this classroom: you are not a member");
    }
}
