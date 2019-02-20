package hwsc.service;

import hwsc.dto.HomeworkDto;
import hwsc.model.Homework;

public interface HomeworkService {

    void assign(String username, long homeworkTemplateId, long classroomId, HomeworkDto dto);

    void finish(String username, long id);

    HomeworkDto get(long id);

    Homework getEntity(long id);
}
