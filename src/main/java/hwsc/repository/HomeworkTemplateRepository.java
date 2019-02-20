package hwsc.repository;

import hwsc.model.HomeworkTemplate;
import hwsc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkTemplateRepository extends JpaRepository<HomeworkTemplate, Long> {

    List<HomeworkTemplate> findAllByCreator(User creator);
}
