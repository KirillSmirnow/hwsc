package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.core.model.*;
import org.smirnowku.hwsc.core.repository.AssignmentRepository;
import org.smirnowku.hwsc.core.repository.HomeworkRepository;
import org.smirnowku.hwsc.core.repository.HomeworkSolutionRepository;
import org.smirnowku.hwsc.core.repository.TaskRepository;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HomeworkService {

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

    public void assign(String username, long homeworkTemplateId, long classroomId, HomeworkDto dto) {
        HomeworkTemplate homeworkTemplate = homeworkTemplateService.getEntity(username, homeworkTemplateId);
        Classroom classroom = classroomService.getEntity(username, classroomId);
        User user = userService.getEntity(username);
        authorizeAssign(classroom, user);
        validateSubgroupSize(classroom, dto.getSubgroupSize());
        Homework homework = new Homework(homeworkTemplate, classroom, createTasks(homeworkTemplate),
                dto.getDeadline(), dto.getSubgroupSize());
        homeworkRepository.save(homework);
        assignHomeworkToStudents(homework, classroom);
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
            homeworkSolutionRepository.save(homeworkSolution);
            Assignment assignment = new Assignment(student, homework, homeworkSolution);
            assignmentRepository.save(assignment);
        });
    }

    private void validateSubgroupSize(Classroom classroom, Integer subgroupSize) {
        int studentsQty = classroom.getStudents().size();
        if (subgroupSize < 2 || subgroupSize > studentsQty)
            throw new IllegalArgumentException(String.format("Subgroup size must be between 2 and %d", studentsQty),
                    String.format("Subgroup size must be between 2 and %d, actual = %d", studentsQty, subgroupSize));
    }

    private void authorizeAssign(Classroom classroom, User user) {
        if (!classroom.getTeachers().contains(user))
            throw new ForbiddenException("You are not allowed to assign homework in this classroom: you are not a teacher");
    }
}
