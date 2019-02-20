package hwsc.model;

import hwsc.HwscException;
import hwsc.dto.TaskSolutionDto;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TaskSolution extends BaseEntity {

    private static final int MAX_LINK_LENGTH = 200;

    @Column(length = MAX_LINK_LENGTH)
    private String link;

    public TaskSolution() {
    }

    public TaskSolution(String link) {
        setLink(link);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        validateLink(link);
        this.link = link;
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

    private void validateLink(String link) {
        if (link != null && link.length() > MAX_LINK_LENGTH)
            throw new HwscException(String.format("Link is too long (max length is %d)", MAX_LINK_LENGTH),
                    String.format("Link is too long (max length is %d, current length is %d)", MAX_LINK_LENGTH, link.length()));
    }
}
