package ntnu.idi.idatt2106.pilt.features.notebook.dto;

import ntnu.idi.idatt2106.pilt.features.notebook.Notebook;
import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Stoppingplace;
import ntnu.idi.idatt2106.pilt.features.user.model.Student;
import org.springframework.stereotype.Component;

/**
 * Maps between Notebook entities and request/response DTOs.
 */
@Component
public class NotebookMapper {

    /**
     * Creates a new Notebook entity from a request.
     *
     * @param request        the client's request payload
     * @param student        the authenticated student (from UserPrincipal)
     * @param stoppingPlace  the resolved Stoppingplace entity
     * @return a new Notebook entity ready to be persisted
     */
    public Notebook toEntity(NotebookRequest request, Student student, Stoppingplace stoppingPlace) {
        Notebook notebook = new Notebook();
        notebook.setStudent(student);
        notebook.setStoppingPlace(stoppingPlace);
        notebook.setReflections(request.reflection());
        return notebook;
    }

    /**
     * Updates only the reflection field on an existing notebook entity.
     *
     * @param notebook the existing entity to update
     * @param request  the client's request payload with the new reflection
     */
    public void updateReflection(Notebook notebook, NotebookRequest request) {
        notebook.setReflections(request.reflection());
    }

    /**
     * Converts a Notebook entity to a response DTO.
     *
     * @param notebook the entity to convert
     * @return a DTO safe to return to the client
     */
    public NotebookResponse toResponse(Notebook notebook) {
        return new NotebookResponse(
            notebook.getId(),
            notebook.getStoppingPlace().getId(),
            notebook.getStoppingPlace().getName(),
            notebook.getTips(),
            notebook.getReflections(),
            notebook.getCreatedAt(),
            notebook.getUpdatedAt()
        );
    }
}
