package ntnu.idi.idatt.2106.pilt.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@Setter
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public interface User {

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
    private String displayName;

    @Column(nullable = false)
    @NotBlank
    private String password;
}