package ntnu.idi.idatt2106.pilt.features.badge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ntnu.idi.idatt2106.pilt.features.user.model.Student;

import java.time.LocalDateTime;

/**
 * Represents the fact that a specific student earned a specific badge at a specific time.
 * <p>
 * This is the many-to-many join entity between {@link Student} and {@link Badge}.
 * Using an explicit entity (rather than {@code @ManyToMany}) because we need
 * extra data on the relationship: when the badge was earned.
 * <p>
 * The unique constraint on (student, badge) prevents a student from earning the
 * same badge twice.
 */
@Entity
@Getter
@Setter
@Table(
    name = "student_badges",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "badge_id"})
)
public class StudentBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The student who earned this badge.
     * LAZY is the correct default — when loading a list of StudentBadges,
     * we don't want to trigger N+1 queries for each Student.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull
    private Student student;

    /**
     * The badge definition that was earned.
     * LAZY because badge details can be fetched separately when needed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = false)
    @NotNull
    private Badge badge;

    /**
     * Timestamp of when the badge was earned.
     * Useful for the teacher dashboard (see when students hit milestones)
     * and for sorting the student's badge collection chronologically.
     */
    @Column(nullable = false)
    @NotNull
    private LocalDateTime earnedAt;

    /**
     * Sets earnedAt to now if not already set. Called automatically before persist.
     */
    @PrePersist
    protected void onCreate() {
        if (earnedAt == null) {
            earnedAt = LocalDateTime.now();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentBadge that = (StudentBadge) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
