package hwsc.repository;

import hwsc.model.Assignment;
import hwsc.model.Check;
import hwsc.model.Homework;
import hwsc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Long> {

    Check findByAssignment(Assignment assignment);

    List<Check> findByCheckerAndAssignment_Homework(User checker, Homework homework);

    List<Check> findAllByCheckerAndStatusIn(User checker, Check.Status... statuses);
}
