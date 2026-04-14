package ntnu.idi.idatt2106.pilt.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

/**
 * Abstract class representing a user in the system. This class is extended by both Teacher and Student classes.
 * The role is derived from the subclass type, not stored as a separate field, to prevent inconsistent state.
 */
@Entity
@Getter
@Setter
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String firstName;

    @Column(nullable = false)
    @NotBlank
    private String lastName;

    @Column(nullable = false)
    @NotBlank
    private String password;

    /**
     * Returns the role of this user, determined by the subclass type.
     * This ensures a Student always has STUDENT role and a Teacher always has TEACHER role.
     */
    public abstract Role getRole();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}