package hwsc.repository;

import hwsc.model.HomeworkSolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkSolutionRepository extends JpaRepository<HomeworkSolution, Long> {
}
