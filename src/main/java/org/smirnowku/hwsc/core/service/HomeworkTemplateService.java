package org.smirnowku.hwsc.core.service;

import org.smirnowku.hwsc.core.model.HomeworkTemplate;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;

import java.util.List;

public interface HomeworkTemplateService {

    void create(String username, HomeworkTemplateDto dto);

    void edit(String username, long id, HomeworkTemplateDto dto);

    List<HomeworkTemplateDto> get(String username);

    void delete(String username, long id);

    HomeworkTemplateDto get(String username, long id);

    HomeworkTemplate getEntity(String username, long id);
}
