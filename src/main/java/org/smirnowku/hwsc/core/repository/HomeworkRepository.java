package org.smirnowku.hwsc.core.repository;

import org.smirnowku.hwsc.core.model.Classroom;
import org.smirnowku.hwsc.core.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    List<Homework> findAllByClassroom(Classroom classroom);
}
