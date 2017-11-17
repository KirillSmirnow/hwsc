package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
