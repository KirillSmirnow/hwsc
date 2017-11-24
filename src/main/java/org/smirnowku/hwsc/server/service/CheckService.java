package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.exception.NotFoundException;
import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.model.Check;
import org.smirnowku.hwsc.server.model.Homework;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.model.dto.CheckResultDto;
import org.smirnowku.hwsc.server.repository.AssignmentRepository;
import org.smirnowku.hwsc.server.repository.CheckRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckService {

    @Resource
    private UserService userService;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private CheckRepository checkRepository;

    public void submit(String username, long id, CheckResultDto dto) {
        Check check = get(username, id);
        check.setStatus(Check.Status.CHECKED);
        checkRepository.save(check);
        Assignment assignment = check.getAssignment();
        assignment.setScore(dto.getScore());
        assignmentRepository.save(assignment);
        onCheckSubmitted(check.getChecker(), assignment.getStudent(), assignment.getHomework());
    }

    public Check get(String username, long id) {
        Check check = checkRepository.findOne(id);
        if (check == null) throw new NotFoundException("Check not found");
        return check;
    }

    public List<Check> getPending(String username) {
        User checker = userService.get(username);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.PENDING);
    }

    public List<Check> getChecked(String username) {
        User checker = userService.get(username);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.CHECKED);
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
}
