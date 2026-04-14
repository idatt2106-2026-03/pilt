package ntnu.idi.idatt2106.pilt.features.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import ntnu.idi.idatt2106.pilt.features.classroom.Classroom;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "students")
public class Student extends User {

    private int level;

    private int totalScore;

    @Column(nullable = false)
    @NotBlank
    private String feideUsername;

    @Column(nullable = false)
    @NotBlank
    private String displayName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    //@OneToOne(cascade = CascadeType.ALL)
    //private Avatar avatar;

    //@OneToMany(mappedBy = "student")
    //private List<Progress> progress;

    @Override
    public Role getRole() {
        return Role.STUDENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student student)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return level == student.level
            && totalScore == student.totalScore
            && Objects.equals(feideUsername, student.feideUsername)
            && Objects.equals(displayName, student.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), level, totalScore, feideUsername, displayName);
    }
}
