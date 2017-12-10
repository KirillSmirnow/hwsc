package org.smirnowku.hwsc.core.service;

import org.smirnowku.hwsc.core.model.Homework;
import org.smirnowku.hwsc.dto.HomeworkDto;

public interface HomeworkService {

    void assign(String username, long homeworkTemplateId, long classroomId, HomeworkDto dto);

    void finish(String username, long id);

    HomeworkDto get(long id);

    Homework getEntity(long id);
}
