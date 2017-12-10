package org.smirnowku.hwsc.core.service;

import org.smirnowku.hwsc.core.model.Homework;
import org.smirnowku.hwsc.dto.AssignmentDto;

import java.util.List;

public interface AssignmentService {

    void submit(String username, long id);

    List<AssignmentDto> getToDo(String username);

    List<AssignmentDto> getSubmitted(String username);

    List<AssignmentDto> getCompleted(String username);

    AssignmentDto get(String username, long id);

    void onAssignmentSubmitted(Homework homework);
}
