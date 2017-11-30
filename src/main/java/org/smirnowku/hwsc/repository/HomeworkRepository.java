package org.smirnowku.hwsc.repository;

import org.smirnowku.hwsc.model.Classroom;
import org.smirnowku.hwsc.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    List<Homework> findAllByClassroom(Classroom classroom);
}
