package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.Assignment;
import org.smirnowku.hwsc.core.model.Check;
import org.smirnowku.hwsc.core.model.Homework;
import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.AssignmentRepository;
import org.smirnowku.hwsc.core.repository.CheckRepository;
import org.smirnowku.hwsc.core.service.AssignmentService;
import org.smirnowku.hwsc.core.service.UserService;
import org.smirnowku.hwsc.dto.AssignmentDto;
import org.smirnowku.hwsc.dto.BaseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    @Resource
    private UserService userService;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private CheckRepository checkRepository;

    @Override
    public void submit(String username, long id) {
        Assignment assignment = getEntity(username, id);
        authorizeSubmit(assignment);
        assignment.setStatus(Assignment.Status.SUBMITTED);
        assignmentRepository.save(assignment);
        onAssignmentSubmitted(assignment.getHomework());
    }

    @Override
    public List<AssignmentDto> getToDo(String username) {
        User user = userService.getEntity(username);
        return assignmentRepository.findAllByStudentAndStatusIn(user, Assignment.Status.TODO).stream()
                .map(Assignment::toDto)
                .sorted(comparing(a -> a.getHomework().getDeadline()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDto> getSubmitted(String username) {
        User user = userService.getEntity(username);
        return assignmentRepository.findAllByStudentAndStatusIn(user, Assignment.Status.SUBMITTED, Assignment.Status.CHECKING).stream()
                .map(Assignment::toDto)
                .sorted(comparing(BaseDto::getUpdated))
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDto> getCompleted(String username) {
        User user = userService.getEntity(username);
        return assignmentRepository.findAllByStudentAndStatusIn(user, Assignment.Status.COMPLETED).stream()
                .map(Assignment::toDto)
                .sorted(comparing(BaseDto::getUpdated).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentDto get(String username, long id) {
        return getEntity(username, id).toDto();
    }

    private Assignment getEntity(String username, long id) {
        Assignment assignment = assignmentRepository.findOne(id);
        if (assignment == null) throw new NotFoundException("Assignment not found");
        User user = userService.getEntity(username);
        authorizeRead(assignment, user);
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
        }
    }

    private void authorizeRead(Assignment assignment, User user) {
        if (!assignment.getStudent().equals(user))
            throw new ForbiddenException("You are not allowed to access this assignment");
    }

    private void authorizeSubmit(Assignment assignment) {
        if (assignment.getStatus() != Assignment.Status.TODO)
            throw new ForbiddenException("This assignment has already been submitted");
    }
}
