package ntnu.idi.idatt2106.pilt.features.notebook;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ntnu.idi.idatt2106.pilt.core.dto.ApiResponse;
import ntnu.idi.idatt2106.pilt.features.auth.UserPrincipal;
import ntnu.idi.idatt2106.pilt.features.notebook.dto.NotebookRequest;
import ntnu.idi.idatt2106.pilt.features.notebook.dto.NotebookResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notebook")
@RequiredArgsConstructor
public class NotebookController {

    private final NotebookService notebookService;

    @PutMapping("/notebooks")
    @Operation(summary = "Write or update a notebook reflection for a specific stoppested",
               description = "Allows the authenticated student to write or update their free-text reflection for a given stoppested. " +
                             "The request must include the ID of the stoppested and the new reflection text. " +
                             "If the reflection field is null or blank, it will clear the existing reflection.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reflection successfully written or updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotebookResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Stoppingsted not found or user has no notebook entry for it",
                    content = @Content(mediaType = "application/json"))
    })
    public ApiResponse<NotebookResponse> writeReflection(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody NotebookRequest request
    ) {

    }

}
