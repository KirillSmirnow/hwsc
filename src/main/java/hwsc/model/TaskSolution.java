package hwsc.model;

import hwsc.HwscException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "task_solutions")
@Getter
@NoArgsConstructor
public class TaskSolution extends BaseEntity {

    private static final int MAX_LINK_LENGTH = 200;

    @Column(length = MAX_LINK_LENGTH)
    private String link;

    public TaskSolution(String link) {
        validateLink(link);
        this.link = link;
    }

    private void validateLink(String link) {
        if (link != null && link.length() > MAX_LINK_LENGTH)
            throw new HwscException(String.format("Link is too long (max length is %d)", MAX_LINK_LENGTH),
                    String.format("Link is too long (max length is %d, current length is %d)",
                            MAX_LINK_LENGTH, link.length()));
    }
}
