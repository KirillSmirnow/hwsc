package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.HomeworkTemplate;
import org.smirnowku.hwsc.core.model.TaskTemplate;
import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.HomeworkTemplateRepository;
import org.smirnowku.hwsc.core.repository.TaskTemplateRepository;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkTemplateService {

    @Resource
    private UserService userService;

    @Resource
    private HomeworkTemplateRepository homeworkTemplateRepository;

    @Resource
    private TaskTemplateRepository taskTemplateRepository;

    public void create(String username, HomeworkTemplateDto dto) {
        User creator = userService.get(username);
        HomeworkTemplate homeworkTemplate = new HomeworkTemplate(creator, dto.getName(), dto.getDescription());
        homeworkTemplateRepository.save(homeworkTemplate);
    }

    public void edit(String username, long id, HomeworkTemplateDto dto) {
        HomeworkTemplate homeworkTemplate = get(username, id);
        homeworkTemplate.setName(dto.getName());
        homeworkTemplate.setDescription(dto.getDescription());
        homeworkTemplate.setTaskTemplates(createTaskTemplates(dto));
        homeworkTemplateRepository.save(homeworkTemplate);
    }

    public List<HomeworkTemplate> get(String username) {
        User user = userService.get(username);
        return homeworkTemplateRepository.findAllByCreator(user);
    }

    public void delete(String username, long id) {
        HomeworkTemplate homeworkTemplate = get(username, id);
        homeworkTemplateRepository.delete(homeworkTemplate);
    }

    public HomeworkTemplate get(String username, long id) {
        HomeworkTemplate homeworkTemplate = homeworkTemplateRepository.findOne(id);
        if (homeworkTemplate == null) throw new NotFoundException("Homework template not found");
        User user = userService.get(username);
        authorizeAccess(homeworkTemplate, user);
        return homeworkTemplate;
    }

    private List<TaskTemplate> createTaskTemplates(HomeworkTemplateDto dto) {
        return dto.getTaskTemplates().stream()
                .map(ttDto -> new TaskTemplate(ttDto.getName(), ttDto.getDescription()))
                .peek(taskTemplateRepository::save)
                .collect(Collectors.toList());
    }

    private void authorizeAccess(HomeworkTemplate homeworkTemplate, User user) {
        if (!homeworkTemplate.creator().equals(user))
            throw new ForbiddenException("You are not allowed to access this homework template: you are not its owner");
    }
}
