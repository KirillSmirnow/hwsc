package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.Progress;
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

    public List<Progress> getProgressToDo(long studentId) {
        User student = userService.getUser(studentId);
        return progressRepository.findAllByStudentAndStatusIn(student, Progress.Status.TODO);
    }

    public List<Progress> getProgressSolved(long studentId) {
        User student = userService.getUser(studentId);
        return progressRepository.findAllByStudentAndStatusIn(student, Progress.Status.SOLVED, Progress.Status.ASSIGNED);
    }

    public List<Progress> getProgressCompleted(long studentId) {
        User student = userService.getUser(studentId);
        return progressRepository.findAllByStudentAndStatusIn(student, Progress.Status.COMPLETED);
    }
}
