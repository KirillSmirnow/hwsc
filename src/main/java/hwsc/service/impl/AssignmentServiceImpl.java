package hwsc.service.impl;

import hwsc.HwscException;
import hwsc.dto.AssignmentDto;
import hwsc.dto.BaseDto;
import hwsc.model.Assignment;
import hwsc.model.Check;
import hwsc.model.Homework;
import hwsc.model.User;
import hwsc.repository.AssignmentRepository;
import hwsc.repository.CheckRepository;
import hwsc.service.AssignmentService;
import hwsc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static hwsc.model.Assignment.Status.*;
import static java.util.Comparator.comparing;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final UserService userService;
    private final AssignmentRepository assignmentRepository;
    private final CheckRepository checkRepository;

    @Override
    public void submit(String username, long id) {
        Assignment assignment = getEntity(username, id);
        User user = userService.getEntity(username);
        authorizeSubmit(assignment, user);
        assignment.setStatus(SUBMITTED);
        assignmentRepository.save(assignment);
        onAssignmentSubmitted(assignment.getHomework());
    }

    @Override
    public List<AssignmentDto> getToDo(String username) {
        User user = userService.getEntity(username);
        return assignmentRepository.findAllByStudentAndStatusIn(user, TODO).stream()
                .map(AssignmentDto::of)
                .sorted(comparing(a -> a.getHomework().getDeadline()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDto> getSubmitted(String username) {
        User user = userService.getEntity(username);
        return assignmentRepository.findAllByStudentAndStatusIn(user, SUBMITTED, CHECKING).stream()
                .map(AssignmentDto::of)
                .sorted(comparing(BaseDto::getUpdated))
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDto> getCompleted(String username) {
        User user = userService.getEntity(username);
        return assignmentRepository.findAllByStudentAndStatusIn(user, COMPLETED).stream()
                .map(AssignmentDto::of)
                .sorted(comparing(BaseDto::getUpdated).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentDto get(String username, long id) {
        return AssignmentDto.of(getEntity(username, id));
    }

    @Override
    public void onAssignmentSubmitted(Homework homework) {
        int studentsSolving = assignmentRepository.countByHomeworkAndStatusIn(homework, TODO);
        int studentsReady = assignmentRepository.countByHomeworkAndStatusIn(homework, SUBMITTED);
        if (studentsSolving == 0 || studentsReady == homework.getSubgroupSize() && studentsSolving > 1)
            assignP2PCheck(homework);
    }

    private Assignment getEntity(String username, long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new HwscException("Assignment not found"));
        User user = userService.getEntity(username);
        authorizeRead(assignment, user);
        return assignment;
    }

    private void assignP2PCheck(Homework homework) {
        List<Assignment> assignments = assignmentRepository.findAllByHomeworkAndStatusIn(homework, SUBMITTED);
        Collections.shuffle(assignments);
        for (int assignmentIndex = 0; assignmentIndex < assignments.size(); ++assignmentIndex) {
            User checker = assignments.get(assignmentIndex < assignments.size() - 1 ? assignmentIndex + 1 : 0)
                    .getStudent();
            Assignment assignment = assignments.get(assignmentIndex);
            Check check = new Check(checker, assignment);
            checkRepository.save(check);
            assignment.setStatus(CHECKING);
            assignmentRepository.save(assignment);
        }
    }

    private void authorizeRead(Assignment assignment, User user) {
        if (!assignment.getStudent().equals(user) &&
                !assignment.getHomework().getClassroom().getTeachers().contains(user))
            throw new HwscException("You are not allowed to access this assignment");
    }

    private void authorizeSubmit(Assignment assignment, User user) {
        if (!assignment.getStudent().equals(user))
            throw new HwscException("You are not allowed to submit this assignment");
        if (assignment.getStatus() != TODO)
            throw new HwscException("This assignment has already been submitted");
    }
}
