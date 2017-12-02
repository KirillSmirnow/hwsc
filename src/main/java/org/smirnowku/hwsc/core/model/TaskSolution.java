package org.smirnowku.hwsc.core.model;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;
import org.smirnowku.hwsc.dto.TaskSolutionDto;
import org.smirnowku.hwsc.util.PropertyValidator;

import javax.persistence.Entity;

@Entity
public class TaskSolution extends BaseEntity {

    private String link;

    public TaskSolution() {
    }

    public TaskSolution(String link) {
        setLink(link);
    }

    public void setLink(String link) {
        if (PropertyValidator.isEmpty(link))
            throw new IllegalArgumentException("Link cannot be empty");
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public TaskSolutionDto toDto() {
        return new TaskSolutionDto(getId(), getCreated(), getUpdated(), link);
    }

    @Override
    public String toString() {
        return "TaskSolution{" +
                "link='" + link + '\'' +
                '}';
    }
}
