package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.HomeworkTemplate;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.repository.HomeworkTemplateRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HomeworkTemplateService {

    @Resource
    private UserService userService;

    @Resource
    private HomeworkTemplateRepository homeworkTemplateRepository;

    public List<HomeworkTemplate> getTemplates(long userId) {
        User user = userService.getUser(userId);
        return homeworkTemplateRepository.findAllByCreator(user);
    }
}
