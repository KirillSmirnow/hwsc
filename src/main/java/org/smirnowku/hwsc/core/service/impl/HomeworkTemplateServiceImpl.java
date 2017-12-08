package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.HomeworkTemplate;
import org.smirnowku.hwsc.core.model.TaskTemplate;
import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.HomeworkTemplateRepository;
import org.smirnowku.hwsc.core.repository.TaskTemplateRepository;
import org.smirnowku.hwsc.core.service.HomeworkTemplateService;
import org.smirnowku.hwsc.core.service.UserService;
import org.smirnowku.hwsc.dto.BaseDto;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class HomeworkTemplateServiceImpl implements HomeworkTemplateService {

    @Resource
    private UserService userService;

    @Resource
    private HomeworkTemplateRepository homeworkTemplateRepository;

    @Resource
    private TaskTemplateRepository taskTemplateRepository;

    @Override
    public void create(String username, HomeworkTemplateDto dto) {
        User creator = userService.getEntity(username);
        HomeworkTemplate homeworkTemplate = new HomeworkTemplate(creator, dto.getName(), dto.getDescription());
        homeworkTemplateRepository.save(homeworkTemplate);
    }

    @Override
    public void edit(String username, long id, HomeworkTemplateDto dto) {
        HomeworkTemplate homeworkTemplate = getEntity(username, id);
        homeworkTemplate.setName(dto.getName());
        homeworkTemplate.setDescription(dto.getDescription());
        homeworkTemplate.setTaskTemplates(createTaskTemplates(dto));
        homeworkTemplateRepository.save(homeworkTemplate);
    }

    @Override
    public List<HomeworkTemplateDto> get(String username) {
        User user = userService.getEntity(username);
        return homeworkTemplateRepository.findAllByCreator(user).stream()
                .map(HomeworkTemplate::toDto)
                .sorted(comparing(BaseDto::getUpdated).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String username, long id) {
        HomeworkTemplate homeworkTemplate = getEntity(username, id);
        homeworkTemplateRepository.delete(homeworkTemplate);
    }

    @Override
    public HomeworkTemplateDto get(String username, long id) {
        return getEntity(username, id).toDto();
    }

    @Override
    public HomeworkTemplate getEntity(String username, long id) {
        HomeworkTemplate homeworkTemplate = homeworkTemplateRepository.findOne(id);
        if (homeworkTemplate == null) throw new NotFoundException("Homework template not found");
        User user = userService.getEntity(username);
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
        if (!homeworkTemplate.getCreator().equals(user))
            throw new ForbiddenException("You are not allowed to access this homework template: you are not its owner");
    }
}