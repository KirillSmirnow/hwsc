package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class HomeworkSolution extends BaseEntity {

    @ManyToOne
    private Homework homework;

    @OneToMany
    private List<TaskSolution> taskSolutions;

    public HomeworkSolution() {
    }

    public HomeworkSolution(Homework homework) {
        this.homework = homework;
    }

    public Homework getHomework() {
        return homework;
    }
}
