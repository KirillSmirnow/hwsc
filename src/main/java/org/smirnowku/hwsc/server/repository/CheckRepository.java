package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.model.Check;
import org.smirnowku.hwsc.server.model.Homework;
import org.smirnowku.hwsc.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Long> {

    Check findByAssignment(Assignment assignment);

    List<Check> findByCheckerAndAssignment_Homework(User checker, Homework homework);

    List<Check> findAllByCheckerAndStatusIn(User checker, Check.Status... statuses);
}
