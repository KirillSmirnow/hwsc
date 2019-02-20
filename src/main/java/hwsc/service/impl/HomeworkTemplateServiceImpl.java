package hwsc.service.impl;

import hwsc.HwscException;
import hwsc.dto.BaseDto;
import hwsc.dto.HomeworkTemplateDto;
import hwsc.model.HomeworkTemplate;
import hwsc.model.TaskTemplate;
import hwsc.model.User;
import hwsc.repository.HomeworkTemplateRepository;
import hwsc.repository.TaskTemplateRepository;
import hwsc.service.HomeworkTemplateService;
import hwsc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeworkTemplateServiceImpl implements HomeworkTemplateService {

    private final UserService userService;
    private final HomeworkTemplateRepository homeworkTemplateRepository;
    private final TaskTemplateRepository taskTemplateRepository;

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
                .map(HomeworkTemplateDto::of)
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
        return HomeworkTemplateDto.of(getEntity(username, id));
    }

    @Override
    public HomeworkTemplate getEntity(String username, long id) {
        HomeworkTemplate homeworkTemplate = homeworkTemplateRepository.findById(id)
                .orElseThrow(() -> new HwscException("Homework template not found"));
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
            throw new HwscException("You are not allowed to access this homework template: you are not its owner");
    }
}
