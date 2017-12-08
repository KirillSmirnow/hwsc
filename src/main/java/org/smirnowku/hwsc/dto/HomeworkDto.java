package org.smirnowku.hwsc.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class HomeworkDto extends BaseDto {

    private ClassroomDto classroom;
    private List<TaskDto> tasks;
    private String name;
    private String description;
    private LocalDateTime deadline;
    private Integer subgroupSize;

    public HomeworkDto() {
    }

    public HomeworkDto(LocalDateTime deadline, Integer subgroupSize) {
        this.deadline = deadline;
        this.subgroupSize = subgroupSize;
    }

    public HomeworkDto(long id, Date created, Date updated, ClassroomDto classroom, List<TaskDto> tasks,
                       String name, String description, LocalDateTime deadline, Integer subgroupSize) {
        super(id, created, updated);
        this.classroom = classroom;
        this.tasks = tasks;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.subgroupSize = subgroupSize;
    }

    public ClassroomDto getClassroom() {
        return classroom;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public Integer getSubgroupSize() {
        return subgroupSize;
    }

    @Override
    public String toString() {
        return "HomeworkDto{" +
                "classroom=" + classroom +
                ", tasks=" + tasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", subgroupSize=" + subgroupSize +
                '}';
    }
}
