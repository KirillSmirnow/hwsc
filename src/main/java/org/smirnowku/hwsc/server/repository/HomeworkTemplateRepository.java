package org.smirnowku.hwsc.server.repository;

import org.smirnowku.hwsc.server.model.HomeworkTemplate;
import org.smirnowku.hwsc.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkTemplateRepository extends JpaRepository<HomeworkTemplate, Long> {

    List<HomeworkTemplate> findAllByCreator(User creator);
}
