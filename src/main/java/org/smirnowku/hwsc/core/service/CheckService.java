package org.smirnowku.hwsc.core.service;

import org.smirnowku.hwsc.dto.AssignmentDto;
import org.smirnowku.hwsc.dto.CheckDto;

import java.util.List;

public interface CheckService {

    void submit(String username, long id, AssignmentDto dto);

    CheckDto get(String username, long id);

    List<CheckDto> getPending(String username);

    List<CheckDto> getChecked(String username);
}
