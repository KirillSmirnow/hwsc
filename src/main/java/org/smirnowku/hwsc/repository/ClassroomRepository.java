package org.smirnowku.hwsc.repository;

import org.smirnowku.hwsc.model.Classroom;
import org.smirnowku.hwsc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findAllByTeachers(User teacher);

    List<Classroom> findAllByStudents(User student);
}
