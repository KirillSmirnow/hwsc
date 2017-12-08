package org.smirnowku.hwsc.core.service;

import org.smirnowku.hwsc.dto.HomeworkDto;

public interface HomeworkService {

    void assign(String username, long homeworkTemplateId, long classroomId, HomeworkDto dto);
}
