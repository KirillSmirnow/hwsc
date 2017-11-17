package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.Check;
import org.smirnowku.hwsc.server.model.HomeworkSolution;
import org.smirnowku.hwsc.server.model.Progress;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.repository.CheckRepository;
import org.smirnowku.hwsc.server.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckService {

    @Resource
    private UserService userService;

    @Resource
    private ProgressRepository progressRepository;

    @Resource
    private CheckRepository checkRepository;

    public List<Check> getPending(long userId) {
        User checker = userService.getUser(userId);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.PENDING);
    }

    public List<Check> getChecked(long userId) {
        User checker = userService.getUser(userId);
        return checkRepository.findAllByCheckerAndStatusIn(checker, Check.Status.CHECKED);
    }

    public HomeworkSolution getHomeworkSolutionToCheck(long progressId) {
        return progressRepository.findOne(progressId).homeworkSolution();
    }

    public void submitCheck(long progressId, Integer result) {
        Progress progress = progressRepository.findOne(progressId);
        Check check = checkRepository.findByHomeworkToCheck(progress);
        check.setStatus(Check.Status.CHECKED);
        checkRepository.save(check);
        progress.setResult(result);
        checkIfCompleted();
    }

    private void checkIfCompleted() {
    }
}
