package ntnu.idi.idatt2106.pilt.features.classroom;

import java.util.List;
import ntnu.idi.idatt2106.pilt.core.dto.ApiResponse;
import ntnu.idi.idatt2106.pilt.core.exception.BadRequestException;
import ntnu.idi.idatt2106.pilt.core.exception.ResourceNotFoundException;
import ntnu.idi.idatt2106.pilt.features.classroom.dto.ClassroomRequestDto;
import ntnu.idi.idatt2106.pilt.features.classroom.dto.ClassroomResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * Service layer for classroom operations.
 *
 * <p>This service handles classroom CRUD logic, validates request payloads,
 * maps between entities and DTOs, and enforces business rules such as unique
 * class codes.</p>
 */
@Service
@Transactional
public class ClassroomService {

  private final ClassroomRepository classroomRepository;
  private final ClassroomMapper classroomMapper;

  public ClassroomService(ClassroomRepository classroomRepository, ClassroomMapper classroomMapper) {
    this.classroomRepository = classroomRepository;
    this.classroomMapper = classroomMapper;
  }

  /**
   * Retrieves all classrooms.
   *
   * @return success response containing all classrooms as response DTOs
   */
  @Transactional(readOnly = true)
  public ApiResponse<List<ClassroomResponseDto>> getAllClassrooms() {
    List<ClassroomResponseDto> classrooms = classroomRepository.findAll()
        .stream()
        .map(classroomMapper::toResponseDto)
        .toList();

    return ApiResponse.ok(classrooms);
  }

  /**
   * Retrieves a classroom by its database id.
   *
   * @param id classroom id
   * @return success response with the matching classroom
   * @throws ResourceNotFoundException if no classroom exists with the given id
   */
  @Transactional(readOnly = true)
  public ApiResponse<ClassroomResponseDto> getClassroomById(Long id) {
    Classroom classroom = classroomRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Classroom not found: " + id));

    return ApiResponse.ok(classroomMapper.toResponseDto(classroom));
  }

  /**
   * Retrieves a classroom by its unique class code.
   *
   * @param classCode classroom class code
   * @return success response with the matching classroom
   * @throws ResourceNotFoundException if no classroom exists with the given class code
   */
  @Transactional(readOnly = true)
  public ApiResponse<ClassroomResponseDto> getClassroomByClassCode(String classCode) {
    Classroom classroom = classroomRepository
        .findByClassCode(classCode)
        .orElseThrow(() -> new ResourceNotFoundException("Classroom not found: " + classCode));

    return ApiResponse.ok(classroomMapper.toResponseDto(classroom));
  }

  /**
   * Creates a new classroom.
   *
   * @param request classroom create payload
   * @return success response with the created classroom
   * @throws BadRequestException if the request is invalid or class code already exists
   */
  public ApiResponse<ClassroomResponseDto> createClassroom(ClassroomRequestDto request) {
    validateClassroomRequest(request);

    if (classroomRepository.findByClassCode(request.classCode()).isPresent()) {
      throw new BadRequestException("Classroom already exists: " + request.classCode());
    }

    Classroom classroom = classroomMapper.toEntity(request);

    Classroom savedClassroom = classroomRepository.save(classroom);
    return ApiResponse.ok("Classroom created", classroomMapper.toResponseDto(savedClassroom));
  }

  /**
   * Updates an existing classroom.
   *
   * @param id classroom id to update
   * @param request classroom update payload
   * @return success response with the updated classroom
   * @throws ResourceNotFoundException if the classroom does not exist
   * @throws BadRequestException if the request is invalid or class code conflicts with another classroom
   */
  public ApiResponse<ClassroomResponseDto> updateClassroom(Long id, ClassroomRequestDto request) {
    validateClassroomRequest(request);

    Classroom existingClassroom = classroomRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Classroom not found: " + id));

    classroomRepository
        .findByClassCode(request.classCode())
        .filter(classroom -> !classroom.getId().equals(existingClassroom.getId()))
        .ifPresent(classroom -> {
          throw new BadRequestException("Class code already exists: " + classroom.getClassCode());
        });

    classroomMapper.updateEntity(existingClassroom, request);

    Classroom savedClassroom = classroomRepository.save(existingClassroom);
    return ApiResponse.ok("Classroom updated", classroomMapper.toResponseDto(savedClassroom));
  }

  /**
   * Deletes a classroom by id.
   *
   * @param id classroom id to delete
   * @return success response with the deleted classroom payload
   * @throws ResourceNotFoundException if the classroom does not exist
   */
  public ApiResponse<ClassroomResponseDto> deleteClassroom(Long id) {
    Classroom classroom = classroomRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Classroom not found: " + id));

    classroomRepository.delete(classroom);
    return ApiResponse.ok("Classroom deleted", classroomMapper.toResponseDto(classroom));
  }

  private void validateClassroomRequest(ClassroomRequestDto request) {
    if (request == null) {
      throw new BadRequestException("Classroom cannot be null");
    }

    if (!StringUtils.hasText(request.classCode())) {
      throw new BadRequestException("Class code is required");
    }

    if (!StringUtils.hasText(request.name())) {
      throw new BadRequestException("Class name is required");
    }
  }
}
