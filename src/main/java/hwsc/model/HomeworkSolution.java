package hwsc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "homework_solutions")
@Getter
@Setter
@NoArgsConstructor
public class HomeworkSolution extends BaseEntity {

    @OneToMany
    private List<TaskSolution> taskSolutions;
}
