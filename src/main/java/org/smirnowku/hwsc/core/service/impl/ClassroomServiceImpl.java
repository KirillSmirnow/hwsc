package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.Classroom;
import org.smirnowku.hwsc.core.model.Homework;
import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.ClassroomRepository;
import org.smirnowku.hwsc.core.repository.HomeworkRepository;
import org.smirnowku.hwsc.core.service.ClassroomService;
import org.smirnowku.hwsc.core.service.UserService;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {

    private interface MemberAdder {
        void addMemberToClassroom(Classroom classroom, User user);
    }

    @Resource
    private UserService userService;

    @Resource
    private ClassroomRepository classroomRepository;

    @Resource
    private HomeworkRepository homeworkRepository;

    @Override
    public void create(String teacherUsername, ClassroomDto dto) {
        User teacher = userService.getEntity(teacherUsername);
        Classroom classroom = new Classroom(teacher, dto.getName(), dto.getDescription());
        classroomRepository.save(classroom);
    }

    @Override
    public void addMembers(String username, long id, List<String> studentsUsernames, List<String> teachersUsernames) {
        Classroom classroom = getEntity(username, id);
        User user = userService.getEntity(username);
        authorizeUpdate(classroom, user);
        addMembersToClassroom(classroom, studentsUsernames, this::addStudentToClassroom);
        addMembersToClassroom(classroom, teachersUsernames, this::addTeacherToClassroom);
        classroomRepository.save(classroom);
    }

    @Override
    public void edit(String username, long id, ClassroomDto dto) {
        Classroom classroom = getEntity(username, id);
        User user = userService.getEntity(username);
        authorizeUpdate(classroom, user);
        classroom.setName(dto.getName());
        classroom.setDescription(dto.getDescription());
        classroomRepository.save(classroom);
    }

    @Override
    public ClassroomDto get(String username, long id) {
        return getEntity(username, id).toDto();
    }

    @Override
    public List<ClassroomDto> getClassroomsAsStudent(String studentUsername) {
        User student = userService.getEntity(studentUsername);
        return classroomRepository.findAllByStudents(student).stream()
                .map(Classroom::toDto)
                .sorted(comparing(ClassroomDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassroomDto> getClassroomsAsTeacher(String teacherUsername) {
        User teacher = userService.getEntity(teacherUsername);
        return classroomRepository.findAllByTeachers(teacher).stream()
                .map(Classroom::toDto)
                .sorted(comparing(ClassroomDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<HomeworkDto> getHomeworks(String username, long id) {
        Classroom classroom = getEntity(username, id);
        return homeworkRepository.findAllByClassroom(classroom).stream()
                .map(Homework::toDto)
                .sorted(comparing(HomeworkDto::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Classroom getEntity(String username, long id) {
        Classroom classroom = classroomRepository.findOne(id);
        if (classroom == null) throw new NotFoundException("Classroom not found");
        User user = userService.getEntity(username);
        authorizeRead(classroom, user);
        return classroom;
    }

    private void addMembersToClassroom(Classroom classroom, List<String> usernames, MemberAdder memberAdder) {
        if (usernames == null) return;
        usernames.forEach(username -> {
            User user;
            try {
                user = userService.getEntity(username);
            } catch (BaseException e) {
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
