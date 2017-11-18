package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AssignmentService {

    @Resource
    private UserService userService;

    @Resource
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAssignmentsToDo(long userId) {
        User user = userService.getUser(userId);
        return assignmentRepository.findAllByStudentAndStatusIn(user, Assignment.Status.TODO);
    }

    public List<Assignment> getAssignmentsSubmitted(long userId) {
        User user = userService.getUser(userId);
        return assignmentRepository.findAllByStudentAndStatusIn(user, Assignment.Status.SUBMITTED, Assignment.Status.CHECKING);
    }

    public List<Assignment> getAssignmentsCompleted(long userId) {
        User user = userService.getUser(userId);
        return assignmentRepository.findAllByStudentAndStatusIn(user, Assignment.Status.COMPLETED);
    }

    Assignment getAssignment(long id) {
        return assignmentRepository.findOne(id);
    }
}
