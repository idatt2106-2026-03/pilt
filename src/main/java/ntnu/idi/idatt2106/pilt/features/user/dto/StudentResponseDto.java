package ntnu.idi.idatt2106.pilt.features.user.dto;

import ntnu.idi.idatt2106.pilt.features.user.model.Role;

/**
 * Student-specific response payload returned by the API.
 *
 * @param id user id
 * @param firstName first name
 * @param lastName last name
 * @param role resolved user role
 * @param level current student level
 * @param totalScore accumulated score
 * @param feideUsername feide username
 * @param displayName display name
 * @param classroomId classroom id, if assigned
 */
public record StudentResponseDto(
    Long id,
    String firstName,
    String lastName,
    Role role,
    int level,
    int totalScore,
    String feideUsername,
    String displayName,
    Long classroomId
) {
}

