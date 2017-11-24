package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.exception.NotFoundException;
import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.model.Homework;
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
        return assignment;
    }

    private void onAssignmentSubmitted(Homework homework) {
        int studentsSolving = assignmentRepository.countByHomeworkAndStatusIn(homework, Assignment.Status.TODO);
        int studentsReady = assignmentRepository.countByHomeworkAndStatusIn(homework, Assignment.Status.SUBMITTED);
        if (studentsSolving == 0 || studentsReady == homework.getSubgroupSize() && studentsSolving > 1) {
            assignP2PCheck();
        }
    }

    private void assignP2PCheck() {
        System.out.println("P2P check assigned");
    }
}
