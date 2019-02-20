package hwsc.service.impl;

import hwsc.dto.HomeworkProgressDto;
import hwsc.model.Assignment;
import hwsc.model.Check;
import hwsc.model.Homework;
import hwsc.repository.AssignmentRepository;
import hwsc.repository.CheckRepository;
import hwsc.service.HomeworkProgressService;
import hwsc.service.HomeworkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class HomeworkProgressServiceImpl implements HomeworkProgressService {

    @Resource
    private HomeworkService homeworkService;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private CheckRepository checkRepository;

    @Override
    public List<HomeworkProgressDto> get(long homeworkId) {
        Homework homework = homeworkService.getEntity(homeworkId);
        List<Assignment> assignments = assignmentRepository.findAllByHomework(homework);
        return assignments.stream()
                .map(assignment -> {
                    Check check = checkRepository.findByAssignment(assignment);
                    return new HomeworkProgressDto(assignment.toDto(),
                            check == null ? null : check.getChecker().toDto());
                })
                .sorted(comparing(hp -> hp.getAssignment().getStudent().getName()))
                .collect(Collectors.toList());
    }
}
