package org.smirnowku.hwsc.core.service;

import org.smirnowku.hwsc.dto.HomeworkProgressDto;

import java.util.List;

public interface HomeworkProgressService {

    List<HomeworkProgressDto> get(long homeworkId);
}
