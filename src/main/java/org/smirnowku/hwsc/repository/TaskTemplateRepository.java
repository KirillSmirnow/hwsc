package org.smirnowku.hwsc.repository;

import org.smirnowku.hwsc.model.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
}
