package org.smirnowku.hwsc.core.repository;

import org.smirnowku.hwsc.core.model.HomeworkSolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkSolutionRepository extends JpaRepository<HomeworkSolution, Long> {
}
