package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.Assignment;
import org.smirnowku.hwsc.core.model.Check;
import org.smirnowku.hwsc.core.model.Homework;
import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.AssignmentRepository;
import org.smirnowku.hwsc.core.repository.CheckRepository;
import org.smirnowku.hwsc.core.service.CheckService;
import org.smirnowku.hwsc.core.service.UserService;
import org.smirnowku.hwsc.dto.AssignmentDto;
import org.smirnowku.hwsc.dto.CheckDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CheckServiceImpl implements CheckService {

    @Resource
    private UserService userService;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private CheckRepository checkRepository;

    @Override
    public void submit(String username, long id, AssignmentDto dto) {
        Check check = getEntity(username, id);
        check.setStatus(Check.Status.CHECKED);
        checkRepository.save(check);
        Assignment assignment = check.getAssignment();
        assignment.setScore(dto.getScore());
        assignmentRepository.save(assignment);
        onCheckSubmitted(check.getChecker(), assignment.getStudent(), assignment.getHomework());
    }

    @Override
    public CheckDto get(String username, long id) {
        return getEntity(username, id).toDto();
    }

    @Override
    public List<CheckDto> getPending(String username) {
        User checker = userService.getEntity(username);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.PENDING).stream()
                .map(Check::toDto).collect(Collectors.toList());
    }

    @Override
    public List<CheckDto> getChecked(String username) {
        User checker = userService.getEntity(username);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.CHECKED).stream()
                .map(Check::toDto).collect(Collectors.toList());
    }

    private Check getEntity(String username, long id) {
        Check check = checkRepository.findOne(id);
        if (check == null) throw new NotFoundException("Check not found");
        User user = userService.getEntity(username);
        authorizeAccess(check, user);
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
                checkRepository.findByCheckerAndAssignment_Homework(assignment.getStudent(), assignment.getHomework()).stream()
                        .allMatch(check -> check.getStatus() == Check.Status.CHECKED);
    }

    private void authorizeAccess(Check check, User user) {
        if (!check.getChecker().equals(user))
            throw new ForbiddenException("You are not allowed to access this check: you are not assigned to it");
    }
}
