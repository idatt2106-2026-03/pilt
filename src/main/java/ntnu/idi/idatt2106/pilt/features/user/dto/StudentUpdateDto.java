package ntnu.idi.idatt2106.pilt.features.user.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * Request payload for partially updating a student.
 *
 * <p>All fields are optional; only non-null values are applied.</p>
 *
 * @param firstName updated first name
 * @param lastName updated last name
 * @param password updated password
 * @param displayName updated display name
 * @param level updated level, must be zero or greater
 * @param totalScore updated score, must be zero or greater
 * @param classroomId updated classroom id, must be positive
 */
public record StudentUpdateDto(
    @Size(min = 1, max = 100) String firstName,
    @Size(min = 1, max = 100) String lastName,
    @Size(min = 8, max = 255) String password,
    @Size(min = 1, max = 100) String displayName,
    @PositiveOrZero Integer level,
    @PositiveOrZero Integer totalScore,
    @Positive Long classroomId
) {
}