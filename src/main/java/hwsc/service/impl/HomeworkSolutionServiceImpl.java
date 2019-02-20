package hwsc.service.impl;

import hwsc.HwscException;
import hwsc.dto.HomeworkSolutionDto;
import hwsc.model.Assignment;
import hwsc.model.HomeworkSolution;
import hwsc.model.TaskSolution;
import hwsc.model.User;
import hwsc.repository.AssignmentRepository;
import hwsc.repository.HomeworkSolutionRepository;
import hwsc.repository.TaskSolutionRepository;
import hwsc.service.HomeworkSolutionService;
import hwsc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeworkSolutionServiceImpl implements HomeworkSolutionService {

    private final UserService userService;
    private final AssignmentRepository assignmentRepository;
    private final HomeworkSolutionRepository homeworkSolutionRepository;
    private final TaskSolutionRepository taskSolutionRepository;

    @Override
    public void save(String username, long id, HomeworkSolutionDto dto) {
        HomeworkSolution homeworkSolution = getEntity(username, id);
        homeworkSolution.setTaskSolutions(createTaskSolutions(dto));
        homeworkSolutionRepository.save(homeworkSolution);
    }

    private HomeworkSolution getEntity(String username, long id) {
        HomeworkSolution homeworkSolution = homeworkSolutionRepository.findById(id)
                .orElseThrow(() -> new HwscException("Homework solution not found"));
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
            throw new HwscException("You are not allowed to access this homework solution");
        if (assignment.getStatus() != Assignment.Status.TODO)
            throw new HwscException("This solution has already been submitted");
    }
}
