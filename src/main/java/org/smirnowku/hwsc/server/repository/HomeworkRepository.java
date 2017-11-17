package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
