package ntnu.idi.idatt2106.pilt.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * Request payload for partially updating a teacher.
 *
 * <p>All fields are optional; only non-null values are applied.</p>
 *
 * @param firstName updated first name
 * @param lastName updated last name
 * @param password updated password
 * @param schoolEmail updated school email
 */
public record TeacherUpdateDto(
    @Size(min = 1, max = 100) String firstName,
    @Size(min = 1, max = 100) String lastName,
    @Size(min = 8, max = 255) String password,
    @Email String schoolEmail
) {
}