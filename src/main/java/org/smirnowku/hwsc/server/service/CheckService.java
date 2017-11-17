package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.Check;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.repository.CheckRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckService {

    @Resource
    private UserService userService;

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
}
