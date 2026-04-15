package ntnu.idi.idatt2106.pilt.features.notebook;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ntnu.idi.idatt2106.pilt.core.dto.ApiResponse;
import ntnu.idi.idatt2106.pilt.core.exception.ForbiddenException;
import ntnu.idi.idatt2106.pilt.core.security.UserPrincipal;
import ntnu.idi.idatt2106.pilt.features.notebook.dto.NotebookRequest;
import ntnu.idi.idatt2106.pilt.features.notebook.dto.NotebookResponse;
import ntnu.idi.idatt2106.pilt.features.user.model.Student;

/**
 * REST controller for notebook operations.
 * <p>
 * Student endpoints allow reading and writing personal notebook entries.
 * Teacher endpoints allow viewing student notebooks and classroom reflections.
 */
@RestController
@RequestMapping("/api/notebook")
@RequiredArgsConstructor
@Tag(name = "Notebook", description = "Endpoints for student notebooks and teacher review")
public class NotebookController {

    private final NotebookService notebookService;

    /**
     * Returns all notebook entries for the authenticated student.
     */
    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
        summary = "Get all notebook entries",
        description = "Returns all notebook entries for the authenticated student, "
            + "ordered by stoppested position on the map."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Notebook entries retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = NotebookResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "Unauthorized - user not authenticated",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "Forbidden - only students can access this endpoint",
            content = @Content(mediaType = "application/json"))
    })
    public ApiResponse<List<NotebookResponse>> getMyNotebooks(
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        Student student = requireStudent(principal);
        return notebookService.getNotebooksForStudent(student);
    }

    /**
     * Returns a single notebook entry for a specific stoppested.
     */
    @GetMapping("/{stoppingPlaceId}")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
        summary = "Get a notebook entry for a specific stoppested",
        description = "Returns the notebook entry (tips + reflection) for the given stoppested "
            + "for the authenticated student."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Notebook entry retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = NotebookResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "Unauthorized - user not authenticated",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "Forbidden - only students can access this endpoint",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Stoppested not found or no notebook entry exists for it",
            content = @Content(mediaType = "application/json"))
    })
    public ApiResponse<NotebookResponse> getNotebookEntry(
        @AuthenticationPrincipal UserPrincipal principal,
        @Parameter(description = "ID of the stoppested", example = "3")
        @PathVariable Long stoppingPlaceId
    ) {
        Student student = requireStudent(principal);
        return null;//notebookService.getNotebookEntry(student, stoppingPlaceId);
    }

    /**
     * Writes or updates a student's reflection for a stoppested.
     */
    @PutMapping
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
        summary = "Write or update a notebook reflection",
        description = "Allows the authenticated student to write or update their free-text "
            + "reflection for a given stoppested. If the reflection field is null or blank, "
            + "the existing reflection will be cleared."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Reflection successfully written or updated",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = NotebookResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid request payload",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "Unauthorized - user not authenticated",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "Forbidden - only students can access this endpoint",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Stoppested not found",
            content = @Content(mediaType = "application/json"))
    })
    public ApiResponse<NotebookResponse> writeReflection(
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody NotebookRequest request
    ) {
        Student student = requireStudent(principal);
        return null;//notebookService.writeReflection(student, request);
    }

    /**
     * Returns all notebook entries for a specific student (teacher view).
     */
    @GetMapping("/teacher/student/{studentId}")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(
        summary = "View a student's notebook entries",
        description = "Allows a teacher to view all notebook entries for a specific student, "
            + "ordered by stoppested position on the map."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Student notebook entries retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = NotebookResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "Unauthorized - user not authenticated",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "Forbidden - only teachers can access this endpoint",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Student not found",
            content = @Content(mediaType = "application/json"))
    })
    public ApiResponse<List<NotebookResponse>> getStudentNotebooks(
        @Parameter(description = "ID of the student", example = "12")
        @PathVariable Long studentId
    ) {
        return null;// notebookService.getNotebooksForStudentAsTeacher(studentId);
    }

    /**
     * Returns all notebook entries for all students in a classroom.
     */
    @GetMapping("/teacher/classroom/{classroomId}")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(
        summary = "View all notebook entries in a classroom",
        description = "Returns all notebook entries for every student in the given classroom."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Classroom notebook entries retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = NotebookResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "Unauthorized - user not authenticated",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "Forbidden - only teachers can access this endpoint",
            content = @Content(mediaType = "application/json"))
    })
    public ApiResponse<List<NotebookResponse>> getClassroomNotebooks(
        @Parameter(description = "ID of the classroom", example = "1")
        @PathVariable Long classroomId
    ) {
        return notebookService.getNotebooksForClassroom(classroomId);
    }

    /**
     * Returns only notebook entries that contain reflections for a classroom.
     */
    @GetMapping("/teacher/classroom/{classroomId}/reflections")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(
        summary = "View reflections in a classroom",
        description = "Returns only notebook entries that contain student-written reflections "
            + "for the given classroom. Useful for teachers reviewing student engagement."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Classroom reflections retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = NotebookResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "Unauthorized - user not authenticated",
            content = @Content(mediaType = "application/json")),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "Forbidden - only teachers can access this endpoint",
            content = @Content(mediaType = "application/json"))
    })
    public ApiResponse<List<NotebookResponse>> getClassroomReflections(
        @Parameter(description = "ID of the classroom", example = "1")
        @PathVariable Long classroomId
    ) {
        return notebookService.getReflectionsForClassroom(classroomId);
    }

    /**
     * Extracts the Student from the authenticated principal.
     * Throws 403 if the user is not a student.
     */
    private Student requireStudent(UserPrincipal principal) {
        if (!(principal.getUser() instanceof Student student)) {
            throw new ForbiddenException("Only students can access this resource");
        }
        return student;
    }
}
