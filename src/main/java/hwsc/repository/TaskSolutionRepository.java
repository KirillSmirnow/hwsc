package hwsc.repository;

import hwsc.model.TaskSolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSolutionRepository extends JpaRepository<TaskSolution, Long> {
}
