package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.Check;
import org.smirnowku.hwsc.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Long> {

    List<Check> findAllByCheckerAndStatusIn(User checker, Check.Status... statuses);
}
