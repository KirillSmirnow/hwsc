package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.*;
import org.smirnowku.hwsc.core.repository.*;
import org.smirnowku.hwsc.core.service.*;
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
    private AssignmentService assignmentService;
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
        authorizeUpdate(classroom, user);
        Homework homework = new Homework(homeworkTemplate, classroom, createTasks(homeworkTemplate),
                dto.getDeadline(), dto.getSubgroupSize());
        homeworkRepository.save(homework);
        assignHomeworkToStudents(homework, classroom);
    }

    @Override
    public void finish(String username, long id) {
        User user = userService.getEntity(username);
        Homework homework = getEntity(id);
        authorizeUpdate(homework.getClassroom(), user);
        authorizeFinish(homework);
        homework.setStatus(Homework.Status.INACTIVE);
        homeworkRepository.save(homework);
        onHomeworkFinish(homework);
    }

    @Override
    public HomeworkDto get(long id) {
        return getEntity(id).toDto();
    }

    @Override
    public Homework getEntity(long id) {
        Homework homework = homeworkRepository.findOne(id);
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

    private void onHomeworkFinish(Homework homework) {
        List<Assignment> assignments = assignmentRepository.findAllByHomework(homework);
        assignments.stream()
                .filter(a -> a.getStatus() == Assignment.Status.TODO)
                .forEach(a -> a.setStatus(Assignment.Status.COMPLETED));
        assignmentRepository.save(assignments);
        assignmentService.onAssignmentSubmitted(homework);
    }

    private void authorizeUpdate(Classroom classroom, User user) {
        if (!classroom.getTeachers().contains(user))
            throw new ForbiddenException("You are not allowed to update homework in this classroom: you are not a teacher");
    }

    private void authorizeFinish(Homework homework) {
        if (homework.getStatus() != Homework.Status.ACTIVE)
            throw new ForbiddenException("This homework is already inactive");
    }
}
