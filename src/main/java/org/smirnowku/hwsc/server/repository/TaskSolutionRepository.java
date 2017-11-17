package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.TaskSolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSolutionRepository extends JpaRepository<TaskSolution, Long> {
}
