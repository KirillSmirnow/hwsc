package org.smirnowku.hwsc.server.service;

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
        HomeworkTemplate homeworkTemplate = new HomeworkTemplate(creator, dto.name, dto.description);
        homeworkTemplateRepository.save(homeworkTemplate);
    }

    public void edit(long userId, long id, HomeworkTemplateDto dto) {
        HomeworkTemplate homeworkTemplate = get(userId, id);
        homeworkTemplate.setName(dto.name);
        homeworkTemplate.setDescription(dto.description);
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
        return homeworkTemplateRepository.findOne(id);
    }

    private List<TaskTemplate> createTaskTemplates(HomeworkTemplateDto dto) {
        return dto.taskTemplates.stream()
                .map(ttDto -> new TaskTemplate(ttDto.name, ttDto.description))
                .peek(taskTemplateRepository::save)
                .collect(Collectors.toList());
    }
}
