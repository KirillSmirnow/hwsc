package hwsc.service;

import hwsc.dto.HomeworkSolutionDto;

public interface HomeworkSolutionService {

    void save(String username, long id, HomeworkSolutionDto dto);
}
