package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.Classroom;
import org.smirnowku.hwsc.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findAllByTeachers(User teacher);

    List<Classroom> findAllByStudents(User student);
}
