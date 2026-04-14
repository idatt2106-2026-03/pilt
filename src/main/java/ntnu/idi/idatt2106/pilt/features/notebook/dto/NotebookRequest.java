package ntnu.idi.idatt2106.pilt.features.notebook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request payload for writing or updating a notebook reflection.
 *
 * @param stoppingPlaceId the StoppingPLace this reflection belongs to
 * @param reflection      the student's free-text reflection (nullable to allow clearing)
 */
@Schema(description = "Request payload for writing or updating a notebook reflection.")
public record NotebookRequest(

    @Schema(description = "ID of the stoppested this reflection is for", example = "3")
    @NotNull
    Long stoppingPlaceId,

    @Schema(description = "The student's free-text reflection. Null or blank clears it.",
            example = "Jeg lærte at man alltid bør sjekke kilden før man deler nyheter.")
    @Size(max = 5000, message = "Reflection cannot exceed 5000 characters")
    String reflection
) {
}
