package org.smirnowku.hwsc.server.model.dto;

import org.smirnowku.hwsc.server.exception.IllegalArgumentException;

public class CheckResultDto {

    private Integer score;

    public Integer getScore() {
        if (PropertyValidator.isEmpty(score))
            throw new IllegalArgumentException("Score cannot be empty");
        return score;
    }
}
