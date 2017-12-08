package org.smirnowku.hwsc.core.repository;

import org.smirnowku.hwsc.core.model.Classroom;
import org.smirnowku.hwsc.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findAllByTeachers(User teacher);

    List<Classroom> findAllByStudents(User student);
}