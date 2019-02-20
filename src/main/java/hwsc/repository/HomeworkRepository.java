package hwsc.repository;

import hwsc.model.Classroom;
import hwsc.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    List<Homework> findAllByClassroom(Classroom classroom);
}
