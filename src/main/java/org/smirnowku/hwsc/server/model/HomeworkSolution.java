package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class HomeworkSolution extends BaseEntity {

    @OneToMany
    private List<TaskSolution> taskSolutions;

    public HomeworkSolution() {
    }

    public void setTaskSolutions(List<TaskSolution> taskSolutions) {
        this.taskSolutions = taskSolutions;
    }

    public List<TaskSolution> getTaskSolutions() {
        return taskSolutions;
    }
}
