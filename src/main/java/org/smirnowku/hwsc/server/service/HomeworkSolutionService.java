package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.exception.ForbiddenException;
import org.smirnowku.hwsc.server.exception.NotFoundException;
import org.smirnowku.hwsc.server.model.HomeworkSolution;
import org.smirnowku.hwsc.server.model.TaskSolution;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.model.dto.HomeworkSolutionDto;
import org.smirnowku.hwsc.server.repository.AssignmentRepository;
import org.smirnowku.hwsc.server.repository.HomeworkSolutionRepository;
import org.smirnowku.hwsc.server.repository.TaskSolutionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkSolutionService {

    @Resource
    private UserService userService;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private HomeworkSolutionRepository homeworkSolutionRepository;

    @Resource
    private TaskSolutionRepository taskSolutionRepository;

    public void save(String username, long id, HomeworkSolutionDto dto) {
        HomeworkSolution homeworkSolution = get(username, id);
        homeworkSolution.setTaskSolutions(createTaskSolutions(dto));
        homeworkSolutionRepository.save(homeworkSolution);
    }

    public HomeworkSolution get(String username, long id) {
        HomeworkSolution homeworkSolution = homeworkSolutionRepository.findOne(id);
        if (homeworkSolution == null) throw new NotFoundException("Homework solution not found");
        User user = userService.get(username);
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
        if (!assignmentRepository.findByHomeworkSolution(homeworkSolution).getStudent().equals(user))
            throw new ForbiddenException("You are not allowed to access this homework solution");
    }
}
