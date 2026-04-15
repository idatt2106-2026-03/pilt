package ntnu.idi.idatt2106.pilt.features.user.dto;

import ntnu.idi.idatt2106.pilt.features.user.model.Role;

/**
 * Teacher-specific response payload returned by the API.
 *
 * @param id user id
 * @param firstName first name
 * @param lastName last name
 * @param role resolved user role
 * @param schoolEmail school email address
 */
public record TeacherResponseDto(
    Long id,
    String firstName,
    String lastName,
    Role role,
    String schoolEmail
) {
}

