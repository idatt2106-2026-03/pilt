package ntnu.idi.idatt2106.pilt.features.notebook;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Stoppingplace;
import ntnu.idi.idatt2106.pilt.features.user.model.Student;
import ntnu.idi.idatt2106.pilt.features.user.model.User;

import java.time.LocalDateTime;

/**
 * Represents a student's notebook, where they can jot down notes, reflections, and clues related to the mysteries they are solving.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notebooks")
@Builder
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Stoppingplace stoppingPlace;


    @Column(columnDefinition = "TEXT")
    private String tips;

    @Column(columnDefinition = "TEXT")
    private String reflections;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notebook notebook = (Notebook) o;
        return id != null && id.equals(notebook.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
