package org.smirnowku.hwsc.repository;

import org.smirnowku.hwsc.model.TaskSolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSolutionRepository extends JpaRepository<TaskSolution, Long> {
}
