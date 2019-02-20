package hwsc.service;

import hwsc.dto.AssignmentDto;
import hwsc.dto.CheckDto;

import java.util.List;

public interface CheckService {

    void submit(String username, long id, AssignmentDto dto);

    CheckDto get(String username, long id);

    List<CheckDto> getPending(String username);

    List<CheckDto> getChecked(String username);
}
