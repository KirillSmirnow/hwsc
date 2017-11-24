package org.smirnowku.hwsc.server.model.dto;

import org.smirnowku.hwsc.server.exception.IllegalArgumentException;

import java.util.Date;

public class HomeworkDto {

    private Date deadline;
    private Integer subgroupSize;

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
