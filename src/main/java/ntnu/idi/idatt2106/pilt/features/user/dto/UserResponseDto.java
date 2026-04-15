package ntnu.idi.idatt2106.pilt.features.user.dto;

import ntnu.idi.idatt2106.pilt.features.user.model.Role;

/**
 * Base user response payload returned by the API.
 *
 * @param id user id
 * @param firstName first name
 * @param lastName last name
 * @param role resolved user role
 */
public record UserResponseDto(
    Long id,
    String firstName,
    String lastName,
    Role role
) {
}

