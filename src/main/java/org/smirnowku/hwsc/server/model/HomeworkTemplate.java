package org.smirnowku.hwsc.server.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class HomeworkTemplate extends BaseEntity {

    @ManyToOne
    private User creator;

    @OneToMany
    private List<TaskTemplate> taskTemplates;

    private String name;
    private String description;

    public HomeworkTemplate() {
    }

    public HomeworkTemplate(User creator, String name, String description) {
        this.creator = creator;
        this.name = name;
        this.description = description;
    }

    public void setTaskTemplates(List<TaskTemplate> taskTemplates) {
        this.taskTemplates = taskTemplates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public List<TaskTemplate> getTaskTemplates() {
        return taskTemplates;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}