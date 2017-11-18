package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
}
