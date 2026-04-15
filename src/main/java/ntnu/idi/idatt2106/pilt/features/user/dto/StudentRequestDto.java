package ntnu.idi.idatt2106.pilt.features.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for creating or updating a student.
 *
 * @param firstName first name
 * @param lastName last name
 * @param password account password
 * @param level current student level
 * @param totalScore accumulated score
 * @param feideUsername feide username
 * @param displayName display name
 * @param classroomId classroom id, if the student is assigned to one
 */
public record StudentRequestDto(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String password,
    @Min(0) int level,
    @Min(0) int totalScore,
    @NotBlank String feideUsername,
    @NotBlank String displayName,
    Long classroomId
) {
}

