package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.Homework;
import org.smirnowku.hwsc.server.model.Progress;
import org.smirnowku.hwsc.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    Progress findByStudentAndHomeworkSolution_Homework(User student, Homework homework);

    List<Progress> findAllByStudentAndStatusIn(User student, Progress.Status... statuses);
}
