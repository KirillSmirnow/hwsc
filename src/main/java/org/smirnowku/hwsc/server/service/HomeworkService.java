package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.*;
import org.smirnowku.hwsc.server.model.dto.HomeworkDto;
import org.smirnowku.hwsc.server.repository.AssignmentRepository;
import org.smirnowku.hwsc.server.repository.HomeworkRepository;
import org.smirnowku.hwsc.server.repository.HomeworkSolutionRepository;
import org.smirnowku.hwsc.server.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkService {

    @Resource
    private ClassroomService classroomService;

    @Resource
    private HomeworkTemplateService homeworkTemplateService;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private HomeworkRepository homeworkRepository;

    @Resource
    private HomeworkSolutionRepository homeworkSolutionRepository;

    @Resource
    private TaskRepository taskRepository;

    public void assign(long userId, long homeworkTemplateId, long classroomId, HomeworkDto dto) {
        HomeworkTemplate homeworkTemplate = homeworkTemplateService.get(userId, homeworkTemplateId);
        Classroom classroom = classroomService.get(userId, classroomId);
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
}
