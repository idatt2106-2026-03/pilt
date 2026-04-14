package ntnu.idi.idatt2106.pilt.features.notebook.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Response payload representing a single notebook entry for one toppingplace.
 *
 * @param id                 the notebook entry ID
 * @param stoppingPlaceId    the stoppested this entry belongs to
 * @param stoppingPlaceName  display name of the stoppested (e.g. "Nyhetskvartalet")
 * @param tips               auto-generated tips and theory, null if stoppested not yet started
 * @param reflection         the student's own reflection, null if not yet written
 * @param createdAt          when the entry was first created (first task completed at this stoppested)
 * @param updatedAt          when the entry was last modified (last reflection edit)
 */
@Schema(description = "A single notebook entry for one stoppested.")
public record NotebookResponse(

    @Schema(description = "Notebook entry ID", example = "42")
    Long id,

    @Schema(description = "ID of the stoppested", example = "3")
    Long stoppingPlaceId,

    @Schema(description = "Display name of the stoppested", example = "Nyhetskvartalet")
    String stoppingPlaceName,

    @Schema(description = "Auto-generated tips and theory from completed tasks")
    String tips,

    @Schema(description = "The student's free-text reflection, null if not yet written")
    String reflection,

    @Schema(description = "When this entry was first created")
    LocalDateTime createdAt,

    @Schema(description = "When this entry was last modified")
    LocalDateTime updatedAt
) {

}
