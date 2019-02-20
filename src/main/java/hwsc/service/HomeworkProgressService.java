package hwsc.service;

import hwsc.dto.HomeworkProgressDto;

import java.util.List;

public interface HomeworkProgressService {

    List<HomeworkProgressDto> get(long homeworkId);
}
