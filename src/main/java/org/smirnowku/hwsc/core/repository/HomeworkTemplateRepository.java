package org.smirnowku.hwsc.core.repository;

import org.smirnowku.hwsc.core.model.HomeworkTemplate;
import org.smirnowku.hwsc.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkTemplateRepository extends JpaRepository<HomeworkTemplate, Long> {

    List<HomeworkTemplate> findAllByCreator(User creator);
}
