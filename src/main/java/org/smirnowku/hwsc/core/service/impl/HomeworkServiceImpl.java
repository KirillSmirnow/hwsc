package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.*;
import org.smirnowku.hwsc.core.repository.*;
import org.smirnowku.hwsc.core.service.ClassroomService;
import org.smirnowku.hwsc.core.service.HomeworkService;
import org.smirnowku.hwsc.core.service.HomeworkTemplateService;
import org.smirnowku.hwsc.core.service.UserService;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HomeworkServiceImpl implements HomeworkService {

    @Resource
    private ClassroomService classroomService;

    @Resource
    private HomeworkTemplateService homeworkTemplateService;

    @Resource
    private UserService userService;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private HomeworkRepository homeworkRepository;

    @Resource
    private HomeworkSolutionRepository homeworkSolutionRepository;

    @Resource
    private TaskRepository taskRepository;

    @Resource
    private TaskSolutionRepository taskSolutionRepository;

    @Override
    public void assign(String username, long homeworkTemplateId, long classroomId, HomeworkDto dto) {
        HomeworkTemplate homeworkTemplate = homeworkTemplateService.getEntity(username, homeworkTemplateId);
        Classroom classroom = classroomService.getEntity(username, classroomId);
        User user = userService.getEntity(username);
        authorizeAssign(classroom, user);
        Homework homework = new Homework(homeworkTemplate, classroom, createTasks(homeworkTemplate),
                dto.getDeadline(), dto.getSubgroupSize());
        homeworkRepository.save(homework);
        assignHomeworkToStudents(homework, classroom);
    }

    @Override
    public Homework getEntity(long homeworkId) {
        Homework homework = homeworkRepository.findOne(homeworkId);
        if (homework == null) throw new NotFoundException("Homework not found");
        return homework;
    }

    private List<Task> createTasks(HomeworkTemplate homeworkTemplate) {
        return homeworkTemplate.getTaskTemplates().stream()
                .map(Task::new)
                .peek(taskRepository::save)
                .collect(Collectors.toList());
    }

    private void assignHomeworkToStudents(Homework homework, Classroom classroom) {
        classroom.getStudents().forEach(student -> {
            HomeworkSolution homeworkSolution = new HomeworkSolution();
            homeworkSolution.setTaskSolutions(homework.getTasks().stream()
                    .map(task -> new TaskSolution())
                    .peek(taskSolutionRepository::save)
                    .collect(Collectors.toList())
            );
            homeworkSolutionRepository.save(homeworkSolution);
            Assignment assignment = new Assignment(student, homework, homeworkSolution);
            assignmentRepository.save(assignment);
        });
    }

    private void authorizeAssign(Classroom classroom, User user) {
        if (!classroom.getTeachers().contains(user))
            throw new ForbiddenException("You are not allowed to assign homework in this classroom: you are not a teacher");
    }
}
