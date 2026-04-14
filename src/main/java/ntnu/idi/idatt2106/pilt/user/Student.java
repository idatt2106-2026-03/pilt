package ntnu.idi.idatt2106.pilt.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
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

    @Override
    public Role getRole() {
        return Role.STUDENT;
    }

    //@ManyToOne
    //private Classroom classroom;

    //@OneToOne(cascade = CascadeType.ALL)
    //private Avatar avatar;

    //@OneToMany(mappedBy = "student")
    //private List<Progress> progress;
}
