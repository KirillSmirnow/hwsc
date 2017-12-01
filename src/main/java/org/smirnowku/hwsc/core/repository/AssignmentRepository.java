package org.smirnowku.hwsc.core.repository;

import org.smirnowku.hwsc.core.model.Assignment;
import org.smirnowku.hwsc.core.model.Homework;
import org.smirnowku.hwsc.core.model.HomeworkSolution;
import org.smirnowku.hwsc.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    int countByHomeworkAndStatusIn(Homework homework, Assignment.Status... statuses);

    Assignment findByStudentAndHomework(User student, Homework homework);

    Assignment findByHomeworkSolution(HomeworkSolution homeworkSolution);

    List<Assignment> findAllByStudentAndStatusIn(User student, Assignment.Status... statuses);

    List<Assignment> findAllByHomeworkAndStatusIn(Homework homework, Assignment.Status... statuses);
}
