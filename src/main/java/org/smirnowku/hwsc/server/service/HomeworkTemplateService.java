package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.exception.NotFoundException;
import org.smirnowku.hwsc.server.model.HomeworkTemplate;
import org.smirnowku.hwsc.server.model.TaskTemplate;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.model.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.server.repository.HomeworkTemplateRepository;
import org.smirnowku.hwsc.server.repository.TaskTemplateRepository;
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

    public void create(long userId, HomeworkTemplateDto dto) {
        User creator = userService.get(userId);
        HomeworkTemplate homeworkTemplate = new HomeworkTemplate(creator, dto.getName(), dto.getDescription());
        homeworkTemplateRepository.save(homeworkTemplate);
    }

    public void edit(long userId, long id, HomeworkTemplateDto dto) {
        HomeworkTemplate homeworkTemplate = get(userId, id);
        homeworkTemplate.setName(dto.getName());
        homeworkTemplate.setDescription(dto.getDescription());
        homeworkTemplate.setTaskTemplates(createTaskTemplates(dto));
        homeworkTemplateRepository.save(homeworkTemplate);
    }

    public List<HomeworkTemplate> get(long userId) {
        User user = userService.get(userId);
        return homeworkTemplateRepository.findAllByCreator(user);
    }

    public void delete(long userId, long id) {
        HomeworkTemplate homeworkTemplate = get(userId, id);
        homeworkTemplateRepository.delete(homeworkTemplate);
    }

    HomeworkTemplate get(long userId, long id) {
        HomeworkTemplate homeworkTemplate = homeworkTemplateRepository.findOne(id);
        if (homeworkTemplate == null) throw new NotFoundException("Homework template not found");
        return homeworkTemplate;
    }

    private List<TaskTemplate> createTaskTemplates(HomeworkTemplateDto dto) {
        return dto.getTaskTemplates().stream()
                .map(ttDto -> new TaskTemplate(ttDto.getName(), ttDto.getDescription()))
                .peek(taskTemplateRepository::save)
                .collect(Collectors.toList());
    }
}
