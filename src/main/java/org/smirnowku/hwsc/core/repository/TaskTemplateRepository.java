package org.smirnowku.hwsc.core.repository;

import org.smirnowku.hwsc.core.model.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
}
