package hwsc.repository;

import hwsc.model.Assignment;
import hwsc.model.Homework;
import hwsc.model.HomeworkSolution;
import hwsc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    int countByHomeworkAndStatusIn(Homework homework, Assignment.Status... statuses);

    Assignment findByStudentAndHomework(User student, Homework homework);

    Assignment findByHomeworkSolution(HomeworkSolution homeworkSolution);

    List<Assignment> findAllByHomework(Homework homework);

    List<Assignment> findAllByStudentAndStatusIn(User student, Assignment.Status... statuses);

    List<Assignment> findAllByHomeworkAndStatusIn(Homework homework, Assignment.Status... statuses);
}
