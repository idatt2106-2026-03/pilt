package ntnu.idi.idatt2106.pilt.user;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Class representing a teacher in the system. This class extends the User class and adds specific attributes and relationships for teachers.
 */
@Entity
@Table(name = "teachers")
public class Teacher extends User {
    private String schoolEmail;

    //@OneToMany(mappedBy = "teacher")
    //private List<Classroom> classrooms;
}
