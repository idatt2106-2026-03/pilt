package ntnu.idi.idatt2106.pilt.features.classroom.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for creating or updating a classroom.
 *
 * @param name classroom display name
 * @param classCode unique classroom code
 */
public record ClassroomRequestDto(
    @NotBlank String name,
    @NotBlank String classCode
) {
}

