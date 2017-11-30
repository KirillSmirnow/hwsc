package org.smirnowku.hwsc.service;

import org.smirnowku.hwsc.exception.ForbiddenException;
import org.smirnowku.hwsc.exception.NotFoundException;
import org.smirnowku.hwsc.model.HomeworkSolution;
import org.smirnowku.hwsc.model.TaskSolution;
import org.smirnowku.hwsc.model.User;
import org.smirnowku.hwsc.model.dto.HomeworkSolutionDto;
import org.smirnowku.hwsc.repository.AssignmentRepository;
import org.smirnowku.hwsc.repository.HomeworkSolutionRepository;
import org.smirnowku.hwsc.repository.TaskSolutionRepository;
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
