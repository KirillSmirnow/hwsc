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

    HomeworkTemplate get(String username, long id) {
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
