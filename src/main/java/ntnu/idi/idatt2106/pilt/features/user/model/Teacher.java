package ntnu.idi.idatt2106.pilt.features.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import ntnu.idi.idatt2106.pilt.features.classroom.Classroom;

/**
 * Class representing a teacher in the system. This class extends the User class and adds specific
 * attributes and relationships for teachers.
 */
@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher extends User {

    @Email
    @Column(nullable = false, unique = true)
    private String schoolEmail;

    @ManyToMany(mappedBy = "teachers")
    private Set<Classroom> classrooms = new HashSet<>();

    @Override
    public Role getRole() {
        return Role.TEACHER;
    }
}
