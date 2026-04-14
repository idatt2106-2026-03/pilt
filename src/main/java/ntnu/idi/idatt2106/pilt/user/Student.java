package ntnu.idi.idatt2106.pilt.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends User {
    private int level;
    private int totalScore;

    //@ManyToOne
    //private Classroom classroom;

    //@OneToOne(cascade = CascadeType.ALL)
    //private Avatar avatar;

    //@OneToMany(mappedBy = "student")
    //private List<Progress> progress;
}
