package ntnu.idi.idatt2106.pilt.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

/**
 * Class representing a teacher in the system. This class extends the User class and adds specific attributes and relationships for teachers.
 */
@Entity
@Table(name = "teachers")
public class Teacher extends User {

    @Email
    @Column(nullable = false, unique = true)
    private String schoolEmail;

    @Override
    public Role getRole() {
        return Role.TEACHER;
    }

    //@OneToMany(mappedBy = "teacher")
    //private List<Classroom> classrooms;
}
