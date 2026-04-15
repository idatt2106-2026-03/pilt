package ntnu.idi.idatt2106.pilt.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for creating or updating a teacher.
 *
 * @param firstName first name
 * @param lastName last name
 * @param password account password
 * @param schoolEmail school email address
 */
public record TeacherRequestDto(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String password,
    @Email @NotBlank String schoolEmail
) {
}

