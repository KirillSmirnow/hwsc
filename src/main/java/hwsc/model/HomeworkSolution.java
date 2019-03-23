package hwsc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "homework_solutions")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class HomeworkSolution extends BaseEntity {

    @OneToMany
    private List<TaskSolution> taskSolutions;
}
