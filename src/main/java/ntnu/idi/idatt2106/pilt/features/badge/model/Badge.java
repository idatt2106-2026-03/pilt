package ntnu.idi.idatt2106.pilt.features.badge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a badge definition — a template describing what can be earned.
 * <p>
 * Badges are predefined in the system (seeded via Flyway or data initializer).
 * Students earn badges by completing stoppesteder, submitting weekly mysteries, etc.
 * <p>
 * A Badge is NOT tied to a specific student — that relationship lives in {@link StudentBadge}.
 * This separation allows the frontend to show all badges (earned and unearned) on the
 * "Mine merker" screen.
 */
@Entity
@Getter
@Setter
@Table(name = "badges")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Display name shown to the student, e.g., "Nyhets-nansen" or "Bilde-eksperten".
     */
    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;

    /**
     * Short description explaining what the badge is for.
     * E.g., "Avslarte falsk nyhet" (from the mockup on p.14).
     */
    @Column(nullable = false)
    @NotBlank
    private String description;

    /**
     * Path or URL to the badge icon image.
     * Used on the "Mine merker" screen and in celebration animations.
     */
    private String imageUrl;

    /**
     * Determines how this badge is earned, which drives the awarding logic
     * in the service layer.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private BadgeType type;

    //private StoppingPlace stoppingPlace; // Optional: only set for badges tied to a specific stoppested

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return id != null && id.equals(badge.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
