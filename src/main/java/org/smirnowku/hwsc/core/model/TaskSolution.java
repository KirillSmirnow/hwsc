package org.smirnowku.hwsc.core.model;

import javax.persistence.Entity;

@Entity
public class TaskSolution extends BaseEntity {

    private String link;

    public TaskSolution() {
    }

    public TaskSolution(String link) {
        this.link = link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "TaskSolution{" +
                "link='" + link + '\'' +
                '}';
    }
}
