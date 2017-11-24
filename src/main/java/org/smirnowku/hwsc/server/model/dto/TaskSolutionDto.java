package org.smirnowku.hwsc.server.model.dto;

import org.smirnowku.hwsc.server.exception.IllegalArgumentException;

public class TaskSolutionDto {

    private String link;

    public String getLink() {
        if (PropertyValidator.isEmpty(link))
            throw new IllegalArgumentException("Link cannot be empty");
        return link;
    }
}
