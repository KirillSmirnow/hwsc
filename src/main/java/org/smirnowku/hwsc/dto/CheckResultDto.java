package org.smirnowku.hwsc.dto;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;

public class CheckResultDto {

    private Integer score;

    public CheckResultDto() {
    }

    public Integer getScore() {
        if (PropertyValidator.isEmpty(score))
            throw new IllegalArgumentException("Score cannot be empty");
        return score;
    }

    @Override
    public String toString() {
        return "CheckResultDto{" +
                "score=" + score +
                '}';
    }
}
