package org.smirnowku.hwsc.repository;

import org.smirnowku.hwsc.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
