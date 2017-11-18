package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.model.Check;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.model.dto.CheckResultDto;
import org.smirnowku.hwsc.server.repository.CheckRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckService {

    @Resource
    private AssignmentService assignmentService;

    @Resource
    private UserService userService;

    @Resource
    private CheckRepository checkRepository;

    public List<Check> getChecksPending(long userId) {
        User checker = userService.getUser(userId);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.PENDING);
    }

    public List<Check> getChecksChecked(long userId) {
        User checker = userService.getUser(userId);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.CHECKED);
    }

    public void submitCheck(long assignmentId, CheckResultDto dto) {
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        Check check = checkRepository.findByAssignment(assignment);
        check.setStatus(Check.Status.CHECKED);
        checkRepository.save(check);
        assignment.setScore(dto.score);
        checkIfCompleted();
    }

    private void checkIfCompleted() {
    }
}
