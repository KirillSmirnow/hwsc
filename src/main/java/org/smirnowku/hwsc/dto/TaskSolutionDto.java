package org.smirnowku.hwsc.dto;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;

public class TaskSolutionDto {

    private String link;

    public String getLink() {
        if (PropertyValidator.isEmpty(link))
            throw new IllegalArgumentException("Link cannot be empty");
        return link;
    }

    @Override
    public String toString() {
        return "TaskSolutionDto{" +
                "link='" + link + '\'' +
                '}';
    }
}
