package hwsc.dto;

import java.util.Date;

public class TaskSolutionDto extends BaseDto {

    private String link;

    public TaskSolutionDto() {
    }

    public TaskSolutionDto(long id, Date created, Date updated, String link) {
        super(id, created, updated);
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "TaskSolutionDto{" +
                "link='" + link + '\'' +
                '}';
    }
}
