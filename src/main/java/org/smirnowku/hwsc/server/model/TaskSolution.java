package org.smirnowku.hwsc.server.model;

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
}
