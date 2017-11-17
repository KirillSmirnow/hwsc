package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.HomeworkSolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkSolutionRepository extends JpaRepository<HomeworkSolution, Long> {
}
