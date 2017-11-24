package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.model.Homework;
import org.smirnowku.hwsc.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    int countByHomeworkAndStatusIn(Homework homework, Assignment.Status... statuses);

    Assignment findByStudentAndHomework(User student, Homework homework);

    List<Assignment> findAllByStudentAndStatusIn(User student, Assignment.Status... statuses);

    List<Assignment> findAllByHomeworkAndStatusIn(Homework homework, Assignment.Status... statuses);
}
