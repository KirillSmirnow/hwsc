package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.model.Homework;
import org.smirnowku.hwsc.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressRepository extends JpaRepository<Assignment, Long> {

    Assignment findByStudentAndHomeworkSolution_Homework(User student, Homework homework);

    List<Assignment> findAllByStudentAndStatusIn(User student, Assignment.Status... statuses);
}
