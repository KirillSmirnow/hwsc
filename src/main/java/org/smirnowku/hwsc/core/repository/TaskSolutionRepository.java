package org.smirnowku.hwsc.core.repository;

import org.smirnowku.hwsc.core.model.TaskSolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSolutionRepository extends JpaRepository<TaskSolution, Long> {
}
