package org.smirnowku.hwsc.core.service;

import org.smirnowku.hwsc.dto.HomeworkSolutionDto;

public interface HomeworkSolutionService {

    void save(String username, long id, HomeworkSolutionDto dto);

    HomeworkSolutionDto get(String username, long id);
}
