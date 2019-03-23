package hwsc.model;

import hwsc.HwscException;
import hwsc.util.PropertyValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "assignments")
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class Assignment extends BaseEntity {

    @ManyToOne(optional = false)
    private User student;

    @ManyToOne(optional = false)
    private Homework homework;

    @OneToOne(optional = false)
    private HomeworkSolution homeworkSolution;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer score;

    public Assignment(User student, Homework homework, HomeworkSolution homeworkSolution) {
        this.student = student;
        this.homework = homework;
        this.homeworkSolution = homeworkSolution;
        this.status = Status.TODO;
        this.score = -1;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setScore(Integer score) {
        validateScore(score);
        this.score = score;
    }

    private void validateScore(Integer score) {
        if (PropertyValidator.isEmpty(score)) throw new HwscException("Score cannot be empty");
        if (score < 0) throw new HwscException("Score cannot be negative");
    }

    public enum Status {
        TODO,
        SUBMITTED,
        CHECKING,
        COMPLETED
    }
}
