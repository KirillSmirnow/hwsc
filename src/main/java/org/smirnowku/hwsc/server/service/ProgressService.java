package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProgressService {

    @Resource
    private UserService userService;

    @Resource
    private ProgressRepository progressRepository;

    public List<Assignment> getProgressToDo(long studentId) {
        User student = userService.getUser(studentId);
        return progressRepository.findAllByStudentAndStatusIn(student, Assignment.Status.TODO);
    }

    public List<Assignment> getProgressSolved(long studentId) {
        User student = userService.getUser(studentId);
        return progressRepository.findAllByStudentAndStatusIn(student, Assignment.Status.SOLVED, Assignment.Status.ASSIGNED);
    }

    public List<Assignment> getProgressCompleted(long studentId) {
        User student = userService.getUser(studentId);
        return progressRepository.findAllByStudentAndStatusIn(student, Assignment.Status.COMPLETED);
    }
}
