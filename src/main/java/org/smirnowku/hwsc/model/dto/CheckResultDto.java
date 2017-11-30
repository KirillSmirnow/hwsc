package org.smirnowku.hwsc.model.dto;

import org.smirnowku.hwsc.exception.IllegalArgumentException;

public class CheckResultDto {

    private Integer score;

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
