package hwsc.service.impl;

import hwsc.HwscException;
import hwsc.dto.AssignmentDto;
import hwsc.dto.BaseDto;
import hwsc.dto.CheckDto;
import hwsc.model.Assignment;
import hwsc.model.Check;
import hwsc.model.Homework;
import hwsc.model.User;
import hwsc.repository.AssignmentRepository;
import hwsc.repository.CheckRepository;
import hwsc.service.CheckService;
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
public class CheckServiceImpl implements CheckService {

    private final UserService userService;
    private final AssignmentRepository assignmentRepository;
    private final CheckRepository checkRepository;

    @Override
    public void submit(String username, long id, AssignmentDto dto) {
        Check check = getEntity(username, id);
        authorizeSubmit(check);
        check.setStatus(Check.Status.CHECKED);
        checkRepository.save(check);
        Assignment assignment = check.getAssignment();
        assignment.setScore(dto.getScore());
        assignmentRepository.save(assignment);
        onCheckSubmitted(check.getChecker(), assignment.getStudent(), assignment.getHomework());
    }

    @Override
    public CheckDto get(String username, long id) {
        return CheckDto.of(getEntity(username, id));
    }

    @Override
    public List<CheckDto> getPending(String username) {
        User checker = userService.getEntity(username);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.PENDING).stream()
                .map(CheckDto::of)
                .sorted(comparing(BaseDto::getUpdated))
                .collect(Collectors.toList());
    }

    @Override
    public List<CheckDto> getChecked(String username) {
        User checker = userService.getEntity(username);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.CHECKED).stream()
                .map(CheckDto::of)
                .sorted(comparing(BaseDto::getUpdated).reversed())
                .collect(Collectors.toList());
    }

    private Check getEntity(String username, long id) {
        Check check = checkRepository.findById(id)
                .orElseThrow(() -> new HwscException("Check not found"));
        User user = userService.getEntity(username);
        authorizeRead(check, user);
        return check;
    }

    private void onCheckSubmitted(User checker, User checkedStudent, Homework homework) {
        checkIfAssignmentCompleted(checker, homework);
        checkIfAssignmentCompleted(checkedStudent, homework);
    }

    private void checkIfAssignmentCompleted(User student, Homework homework) {
        Assignment assignment = assignmentRepository.findByStudentAndHomework(student, homework);
        if (isAssignmentCompleted(assignment)) {
            assignment.setStatus(Assignment.Status.COMPLETED);
            assignmentRepository.save(assignment);
        }
    }

    private boolean isAssignmentCompleted(Assignment assignment) {
        return checkRepository.findByAssignment(assignment).getStatus() == Check.Status.CHECKED &&
                checkRepository.findByCheckerAndAssignment_Homework(assignment.getStudent(), assignment.getHomework())
                        .stream().allMatch(check -> check.getStatus() == Check.Status.CHECKED);
    }

    private void authorizeRead(Check check, User user) {
        if (!check.getChecker().equals(user))
            throw new HwscException("You are not allowed to access this check: you are not assigned to it");
    }

    private void authorizeSubmit(Check check) {
        if (check.getStatus() != Check.Status.PENDING)
            throw new HwscException("This assignment has already been checked");
    }
}
