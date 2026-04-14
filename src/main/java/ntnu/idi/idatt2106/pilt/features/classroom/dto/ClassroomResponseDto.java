package ntnu.idi.idatt2106.pilt.features.classroom.dto;

/**
 * Response payload representing classroom data exposed by the API.
 *
 * @param id classroom id
 * @param name classroom display name
 * @param classCode unique classroom code
 */
public record ClassroomResponseDto(
    Long id,
    String name,
    String classCode
) {
}

