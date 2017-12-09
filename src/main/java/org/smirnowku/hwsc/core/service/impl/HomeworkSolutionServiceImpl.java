package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.Assignment;
import org.smirnowku.hwsc.core.model.HomeworkSolution;
import org.smirnowku.hwsc.core.model.TaskSolution;
import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.AssignmentRepository;
import org.smirnowku.hwsc.core.repository.HomeworkSolutionRepository;
import org.smirnowku.hwsc.core.repository.TaskSolutionRepository;
import org.smirnowku.hwsc.core.service.HomeworkSolutionService;
import org.smirnowku.hwsc.core.service.UserService;
import org.smirnowku.hwsc.dto.HomeworkSolutionDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HomeworkSolutionServiceImpl implements HomeworkSolutionService {

    @Resource
    private UserService userService;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private HomeworkSolutionRepository homeworkSolutionRepository;

    @Resource
    private TaskSolutionRepository taskSolutionRepository;

    @Override
    public void save(String username, long id, HomeworkSolutionDto dto) {
        HomeworkSolution homeworkSolution = getEntity(username, id);
        homeworkSolution.setTaskSolutions(createTaskSolutions(dto));
        homeworkSolutionRepository.save(homeworkSolution);
    }

    private HomeworkSolution getEntity(String username, long id) {
        HomeworkSolution homeworkSolution = homeworkSolutionRepository.findOne(id);
        if (homeworkSolution == null) throw new NotFoundException("Homework solution not found");
        User user = userService.getEntity(username);
        authorizeAccess(homeworkSolution, user);
        return homeworkSolution;
    }

    private List<TaskSolution> createTaskSolutions(HomeworkSolutionDto dto) {
        return dto.getTaskSolutions().stream()
                .map(tsDto -> new TaskSolution(tsDto.getLink()))
                .peek(taskSolutionRepository::save)
                .collect(Collectors.toList());
    }

    private void authorizeAccess(HomeworkSolution homeworkSolution, User user) {
        Assignment assignment = assignmentRepository.findByHomeworkSolution(homeworkSolution);
        if (!assignment.getStudent().equals(user))
            throw new ForbiddenException("You are not allowed to access this homework solution");
        if (assignment.getStatus() != Assignment.Status.TODO)
            throw new ForbiddenException("This solution has already been submitted");
    }
}
