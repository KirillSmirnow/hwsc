package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.HomeworkSolution;
import org.smirnowku.hwsc.server.model.TaskSolution;
import org.smirnowku.hwsc.server.model.dto.HomeworkSolutionDto;
import org.smirnowku.hwsc.server.repository.HomeworkSolutionRepository;
import org.smirnowku.hwsc.server.repository.TaskSolutionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkSolutionService {

    @Resource
    private HomeworkSolutionRepository homeworkSolutionRepository;

    @Resource
    private TaskSolutionRepository taskSolutionRepository;

    public void save(long userId, long id, HomeworkSolutionDto dto) {
        HomeworkSolution homeworkSolution = homeworkSolutionRepository.findOne(id);
        homeworkSolution.setTaskSolutions(createTaskSolutions(dto));
        homeworkSolutionRepository.save(homeworkSolution);
    }

    private List<TaskSolution> createTaskSolutions(HomeworkSolutionDto dto) {
        return dto.taskSolutions.stream()
                .map(tsDto -> new TaskSolution(tsDto.link))
                .peek(taskSolutionRepository::save)
                .collect(Collectors.toList());
    }
}
