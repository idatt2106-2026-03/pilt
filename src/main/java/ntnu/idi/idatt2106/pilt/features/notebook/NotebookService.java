package ntnu.idi.idatt2106.pilt.features.notebook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2106.pilt.core.dto.ApiResponse;
import ntnu.idi.idatt2106.pilt.core.exception.ResourceNotFoundException;
import ntnu.idi.idatt2106.pilt.features.notebook.dto.NotebookMapper;
import ntnu.idi.idatt2106.pilt.features.notebook.dto.NotebookRequest;
import ntnu.idi.idatt2106.pilt.features.notebook.dto.NotebookResponse;
import ntnu.idi.idatt2106.pilt.features.stoppingplace.StoppingplaceRepository;
import ntnu.idi.idatt2106.pilt.features.stoppingplace.model.Stoppingplace;
import ntnu.idi.idatt2106.pilt.features.user.UserRepository;
import ntnu.idi.idatt2106.pilt.features.user.model.Student;
import ntnu.idi.idatt2106.pilt.features.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for notebook operations.
 * <p>
 * Handles all business logic for the notebook feature:
 * <ul>
 *   <li>Students reading their notebook entries</li>
 *   <li>Students writing/updating reflections</li>
 *   <li>System auto-adding tips when tasks are completed</li>
 *   <li>Teachers viewing student notebooks</li>
 * </ul>
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotebookService {

    private final NotebookRepository notebookRepository;
    private final StoppingplaceRepository stoppingplaceRepository;
    private final UserRepository userRepository;
    private final NotebookMapper notebookMapper;

    /**
     * Returns all notebook entries for the authenticated student,
     * ordered by stoppested position on the map.
     *
     * @param student the authenticated student
     * @return list of notebook entries ordered by map position
     */
    @Transactional(readOnly = true)
    public ApiResponse<List<NotebookResponse>> getNotebooksForStudent(Student student) {
        log.info("Fetching notebook entries for student: {}", student.getFeideUsername());
        List<NotebookResponse> entries = notebookRepository
            .findAllByStudentOrderByStoppingPlaceRequiredStarsAsc(student)
            .stream()
            .map(notebookMapper::toResponse)
            .toList();

        return ApiResponse.ok(entries);
    }

    /**
     * Returns a single notebook entry for a specific stoppested.
     *
     * @param student         the authenticated student
     * @param stoppingPlaceId the stoppested to view
     * @return the notebook entry for that stoppested
     * @throws ResourceNotFoundException if no entry exists yet
     */
    @Transactional(readOnly = true)
    public ApiResponse<NotebookResponse> getNotebookEntry(Student student, Long stoppingPlaceId) {
        Stoppingplace stoppingPlace = findStoppingPlaceOrThrow(stoppingPlaceId);
        log.info("Fetching notebook entry for student: {}, stoppested: {}", student.getFeideUsername(), stoppingPlace.getName());
        Notebook notebook = notebookRepository
            .findByStudentAndStoppingPlace(student, stoppingPlace)
                .orElseThrow(() -> {
                    log.warn("No notebook entry found for student: {}, stoppested: {}", student.getFeideUsername(), stoppingPlace.getName());
                    return new ResourceNotFoundException("No notebook entry for stoppested: " + stoppingPlace.getName());
                });

        return ApiResponse.ok(notebookMapper.toResponse(notebook));
    }

    /**
     * Writes or updates a student's reflection for a stoppested.
     *
     * @param student the authenticated student
     * @param request contains stoppingPlaceId and the reflection text
     * @return the updated notebook entry
     */
    public ApiResponse<NotebookResponse> writeReflection(Student student, NotebookRequest request) {
        Stoppingplace stoppingPlace = findStoppingPlaceOrThrow(request.stoppingPlaceId());
        log.info("Writing reflection for student: {}, stoppested: {}", student.getFeideUsername(), stoppingPlace.getName());
        Notebook notebook = notebookRepository
            .findByStudentAndStoppingPlace(student, stoppingPlace)
            .map(existing -> {
                notebookMapper.updateReflection(existing, request);
                return existing;
            })
            .orElseGet(() -> notebookMapper.toEntity(request, student, stoppingPlace));

        Notebook saved = notebookRepository.save(notebook);
        return ApiResponse.ok("Reflection saved", notebookMapper.toResponse(saved));
    }


    /**
     * Adds auto-generated tips to a student's notebook for a stoppested.
     *
     * @param student        the student who completed the task
     * @param stoppingPlace  the stoppested the task belongs to
     * @param tip            the tip text to add
     */
    public void addTip(Student student, Stoppingplace stoppingPlace, String tip) {
        log.info("Adding tip for student: {}, stoppested: {}", student.getFeideUsername(), stoppingPlace.getName());
        Notebook notebook = notebookRepository
            .findByStudentAndStoppingPlace(student, stoppingPlace)
            .orElseGet(() -> {
                Notebook newEntry = new Notebook();
                newEntry.setStudent(student);
                newEntry.setStoppingPlace(stoppingPlace);
                return newEntry;
            });

        String currentTips = notebook.getTips();
        if (currentTips == null || currentTips.isBlank()) {
            notebook.setTips(tip);
        } else {
            notebook.setTips(currentTips + "\n\n" + tip);
        }

        notebookRepository.save(notebook);
    }

    /**
     * Returns all notebook entries for a specific student (teacher view).
     *
     * @param studentId the ID of the student whose notebook to view
     * @return list of notebook entries ordered by map position
     * @throws ResourceNotFoundException if the student does not exist
     */
    @Transactional(readOnly = true)
    public ApiResponse<List<NotebookResponse>> getNotebooksForStudentAsTeacher(Long studentId) {
        Student student = findStudentOrThrow(studentId);
        log.info("Teacher fetching notebook entries for student: {}", student.getFeideUsername());
        return getNotebooksForStudent(student);
    }

    /**
     * Returns all notebook entries for all students in a classroom.
     *
     * @param classroomId the classroom to query
     * @return all notebook entries for students in that classroom
     */
    @Transactional(readOnly = true)
    public ApiResponse<List<NotebookResponse>> getNotebooksForClassroom(Long classroomId) {
        log.info("Teacher fetching all notebook entries for classroom: {}", classroomId);
        List<NotebookResponse> entries = notebookRepository
            .findAllByStudentClassroomId(classroomId)
            .stream()
            .map(notebookMapper::toResponse)
            .toList();

        return ApiResponse.ok(entries);
    }

    /**
     * Returns only notebook entries that have reflections for a classroom.
     *
     * @param classroomId the classroom to query
     * @return notebook entries with non-null reflections
     */
    @Transactional(readOnly = true)
    public ApiResponse<List<NotebookResponse>> getReflectionsForClassroom(Long classroomId) {
        log.info("Teacher fetching notebook entries with reflections for classroom: {}", classroomId);
        List<NotebookResponse> entries = notebookRepository
            .findAllByStudentClassroomIdAndReflectionsIsNotNull(classroomId)
            .stream()
            .map(notebookMapper::toResponse)
            .toList();

        return ApiResponse.ok(entries);
    }


    private Student findStudentOrThrow(Long studentId) {
        User user = userRepository.findById(studentId)
            .orElseThrow(() -> {
                log.warn("User not found with id: {}", studentId);
                return new ResourceNotFoundException("Student not found: " + studentId);
            });

        if (!(user instanceof Student student)) {
            log.warn("User {} is not a student", studentId);
            throw new ResourceNotFoundException("Student not found: " + studentId);
        }
        return student;
    }

    private Stoppingplace findStoppingPlaceOrThrow(Long stoppingPlaceId) {
        log.info("Looking up stoppested with id: {}", stoppingPlaceId);
        return stoppingplaceRepository
            .findById(stoppingPlaceId)
            .orElseThrow(() -> {
                log.warn("Stoppested not found with id: {}", stoppingPlaceId);
                return new ResourceNotFoundException(
                        "Stoppested not found: " + stoppingPlaceId);
            });
    }
}
