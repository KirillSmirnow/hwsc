package hwsc.repository;

import hwsc.model.Classroom;
import hwsc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findAllByTeachers(User teacher);

    List<Classroom> findAllByStudents(User student);
}
