package org.smirnowku.hwsc.dto;

import org.smirnowku.hwsc.core.exception.IllegalArgumentException;

import java.util.Date;

public class HomeworkDto {

    private Date deadline;
    private Integer subgroupSize;

    public HomeworkDto() {
    }

    public Date getDeadline() {
        return deadline;
    }

    public Integer getSubgroupSize() {
        if (PropertyValidator.isEmpty(subgroupSize))
            throw new IllegalArgumentException("Subgroup size cannot be empty");
        return subgroupSize;
    }

    @Override
    public String toString() {
        return "HomeworkDto{" +
                "deadline=" + deadline +
                ", subgroupSize=" + subgroupSize +
                '}';
    }
}
