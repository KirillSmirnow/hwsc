package org.smirnowku.hwsc.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.Assignment;
import org.smirnowku.hwsc.core.model.Check;
import org.smirnowku.hwsc.core.model.Homework;
import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.AssignmentRepository;
import org.smirnowku.hwsc.core.repository.CheckRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class AssignmentService {

    private static final Logger log = LoggerFactory.getLogger(AssignmentService.class);

    @Resource
    private UserService userService;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private CheckRepository checkRepository;

    public void submit(String username, long id) {
        Assignment assignment = get(username, id);
        assignment.setStatus(Assignment.Status.SUBMITTED);
        assignmentRepository.save(assignment);
        onAssignmentSubmitted(assignment.getHomework());
    }

    public List<Assignment> getToDo(String username) {
        User user = userService.get(username);
        return assignmentRepository.findAllByStudentAndStatusIn(user, Assignment.Status.TODO);
    }

    public List<Assignment> getSubmitted(String username) {
        User user = userService.get(username);
        return assignmentRepository.findAllByStudentAndStatusIn(user, Assignment.Status.SUBMITTED, Assignment.Status.CHECKING);
    }

    public List<Assignment> getCompleted(String username) {
        User user = userService.get(username);
        return assignmentRepository.findAllByStudentAndStatusIn(user, Assignment.Status.COMPLETED);
    }

    public Assignment get(String username, long id) {
        Assignment assignment = assignmentRepository.findOne(id);
        if (assignment == null) throw new NotFoundException("Assignment not found");
        User user = userService.get(username);
        authorizeAccess(assignment, user);
        return assignment;
    }

    private void onAssignmentSubmitted(Homework homework) {
        int studentsSolving = assignmentRepository.countByHomeworkAndStatusIn(homework, Assignment.Status.TODO);
        int studentsReady = assignmentRepository.countByHomeworkAndStatusIn(homework, Assignment.Status.SUBMITTED);
        if (studentsSolving == 0 || studentsReady == homework.getSubgroupSize() && studentsSolving > 1) {
            assignP2PCheck(homework);
        }
    }

    private void assignP2PCheck(Homework homework) {
        List<Assignment> assignments = assignmentRepository.findAllByHomeworkAndStatusIn(homework, Assignment.Status.SUBMITTED);
        Collections.shuffle(assignments);
        for (int assignmentIndex = 0; assignmentIndex < assignments.size(); ++assignmentIndex) {
            User checker = assignments.get(assignmentIndex < assignments.size() - 1 ? assignmentIndex + 1 : 0).getStudent();
            Assignment assignment = assignments.get(assignmentIndex);
            Check check = new Check(checker, assignment);
            checkRepository.save(check);
            assignment.setStatus(Assignment.Status.CHECKING);
            assignmentRepository.save(assignment);
            log.info("Check assigned: " + check);
        }
    }

    private void authorizeAccess(Assignment assignment, User user) {
        if (!assignment.getStudent().equals(user))
            throw new ForbiddenException("You are not allowed to access this assignment");
    }
}
