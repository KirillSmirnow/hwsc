package hwsc.service.impl;

import hwsc.dto.AssignmentDto;
import hwsc.dto.HomeworkProgressDto;
import hwsc.dto.UserDto;
import hwsc.model.Assignment;
import hwsc.model.Check;
import hwsc.model.Homework;
import hwsc.repository.AssignmentRepository;
import hwsc.repository.CheckRepository;
import hwsc.service.HomeworkProgressService;
import hwsc.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeworkProgressServiceImpl implements HomeworkProgressService {

    private final HomeworkService homeworkService;
    private final AssignmentRepository assignmentRepository;
    private final CheckRepository checkRepository;

    @Override
    public List<HomeworkProgressDto> get(long homeworkId) {
        Homework homework = homeworkService.getEntity(homeworkId);
        List<Assignment> assignments = assignmentRepository.findAllByHomework(homework);
        return assignments.stream()
                .map(assignment -> {
                    Check check = checkRepository.findByAssignment(assignment);
                    return new HomeworkProgressDto(AssignmentDto.of(assignment),
                            check == null ? null : UserDto.of(check.getChecker()));
                })
                .sorted(comparing(hp -> hp.getAssignment().getStudent().getName()))
                .collect(Collectors.toList());
    }
}
