package org.smirnowku.hwsc.core.repository;

import org.smirnowku.hwsc.core.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
