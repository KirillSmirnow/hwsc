package hwsc.service.impl;

import hwsc.HwscException;
import hwsc.dto.AssignmentDto;
import hwsc.dto.HomeworkProgressDto;
import hwsc.dto.UserDto;
import hwsc.model.Assignment;
import hwsc.model.Check;
import hwsc.model.Homework;
import hwsc.model.User;
import hwsc.repository.AssignmentRepository;
import hwsc.repository.CheckRepository;
import hwsc.service.HomeworkProgressService;
import hwsc.service.HomeworkService;
import hwsc.service.UserService;
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
    private final UserService userService;
    private final AssignmentRepository assignmentRepository;
    private final CheckRepository checkRepository;

    @Override
    public List<HomeworkProgressDto> get(String username, long homeworkId) {
        Homework homework = homeworkService.getEntity(homeworkId);
        User user = userService.getEntity(username);
        List<Assignment> assignments = assignmentRepository.findAllByHomework(homework);
        authorizeView(homework, assignments, user);
        return assignments.stream()
                .map(assignment -> {
                    Check check = checkRepository.findByAssignment(assignment);
                    return new HomeworkProgressDto(AssignmentDto.of(assignment),
                            check == null ? null : UserDto.of(check.getChecker()));
                })
                .sorted(comparing(hp -> hp.getAssignment().getStudent().getName()))
                .collect(Collectors.toList());
    }

    private void authorizeView(Homework homework, List<Assignment> assignments, User user) {
        if (!homework.getClassroom().getTeachers().contains(user)) {
            boolean anyAssignmentNotCompleted = assignments.stream()
                    .map(Assignment::getStatus)
                    .anyMatch(status -> status != Assignment.Status.COMPLETED);
            if (anyAssignmentNotCompleted) {
                throw new HwscException("You are not allowed to view homework statistics yet");
            }
        }
    }
}
