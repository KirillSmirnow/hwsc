package org.smirnowku.hwsc.repository;

import org.smirnowku.hwsc.model.HomeworkTemplate;
import org.smirnowku.hwsc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkTemplateRepository extends JpaRepository<HomeworkTemplate, Long> {

    List<HomeworkTemplate> findAllByCreator(User creator);
}