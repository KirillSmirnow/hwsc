package hwsc.model;

import hwsc.HwscException;
import hwsc.dto.AssignmentDto;
import hwsc.util.PropertyValidator;

import javax.persistence.*;

@Entity
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

    public Assignment() {
    }

    public Assignment(User student, Homework homework, HomeworkSolution homeworkSolution) {
        this.student = student;
        this.homework = homework;
        this.homeworkSolution = homeworkSolution;
        this.status = Status.TODO;
        this.score = -1;
    }

    public User getStudent() {
        return student;
    }

    public Homework getHomework() {
        return homework;
    }

    public HomeworkSolution getHomeworkSolution() {
        return homeworkSolution;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        validateScore(score);
        this.score = score;
    }

    public AssignmentDto toDto() {
        return new AssignmentDto(getId(), getCreated(), getUpdated(), student.toDto(),
                homework.toDto(), homeworkSolution.toDto(), status, score);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "student=" + student +
                ", homework=" + homework +
                ", homeworkSolution=" + homeworkSolution +
                ", status=" + status +
                ", score=" + score +
                '}';
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
