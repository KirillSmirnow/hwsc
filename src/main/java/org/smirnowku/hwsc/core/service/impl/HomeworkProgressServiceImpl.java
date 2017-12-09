package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.model.Assignment;
import org.smirnowku.hwsc.core.model.Check;
import org.smirnowku.hwsc.core.model.Homework;
import org.smirnowku.hwsc.core.repository.AssignmentRepository;
import org.smirnowku.hwsc.core.repository.CheckRepository;
import org.smirnowku.hwsc.core.service.HomeworkProgressService;
import org.smirnowku.hwsc.core.service.HomeworkService;
import org.smirnowku.hwsc.dto.HomeworkProgressDto;
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
                    return new HomeworkProgressDto(assignment.getStudent().toDto(), assignment.getStatus(),
                            check == null ? null : check.getChecker().toDto(), assignment.getScore());
                })
                .sorted(comparing(hp -> hp.getStudent().getName()))
                .collect(Collectors.toList());
    }
}
