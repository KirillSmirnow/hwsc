package hwsc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "checks")
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class Check extends BaseEntity {

    @ManyToOne(optional = false)
    private User checker;

    @OneToOne(optional = false)
    private Assignment assignment;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;

    public Check(User checker, Assignment assignment) {
        this.checker = checker;
        this.assignment = assignment;
        this.status = Status.PENDING;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        PENDING,
        CHECKED
    }
}
