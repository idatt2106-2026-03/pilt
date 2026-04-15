package ntnu.idi.idatt2106.pilt.features.user;

import java.util.List;
import ntnu.idi.idatt2106.pilt.core.dto.ApiResponse;
import ntnu.idi.idatt2106.pilt.core.exception.BadRequestException;
import ntnu.idi.idatt2106.pilt.core.exception.ResourceNotFoundException;
import ntnu.idi.idatt2106.pilt.features.classroom.Classroom;
import ntnu.idi.idatt2106.pilt.features.classroom.ClassroomRepository;
import ntnu.idi.idatt2106.pilt.features.user.dto.StudentRequestDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.StudentResponseDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.StudentUpdateDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.TeacherRequestDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.TeacherResponseDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.TeacherUpdateDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.UserResponseDto;
import ntnu.idi.idatt2106.pilt.features.user.model.Student;
import ntnu.idi.idatt2106.pilt.features.user.model.Teacher;
import ntnu.idi.idatt2106.pilt.features.user.model.User;
import ntnu.idi.idatt2106.pilt.features.user.repository.StudentRepository;
import ntnu.idi.idatt2106.pilt.features.user.repository.TeacherRepository;
import ntnu.idi.idatt2106.pilt.features.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for user operations.
 *
 * <p>Handles retrieval, creation and update flows for students and teachers,
 * including validation of related entities and uniqueness checks.</p>
 */
@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final StudentRepository studentRepository;
  private final TeacherRepository teacherRepository;
  private final ClassroomRepository classroomRepository;
  private final UserMapper userMapper;

  /**
   * Creates a new service instance with required repositories and mapper.
   *
   * @param userRepository repository for generic user lookups
   * @param studentRepository repository for student persistence and queries
   * @param teacherRepository repository for teacher persistence and queries
   * @param classroomRepository repository for classroom lookups
   * @param userMapper mapper for converting between DTOs and entities
   */
  public UserService(
      UserRepository userRepository,
      StudentRepository studentRepository,
      TeacherRepository teacherRepository,
      ClassroomRepository classroomRepository,
      UserMapper userMapper
  ) {
    this.userRepository = userRepository;
    this.studentRepository = studentRepository;
    this.teacherRepository = teacherRepository;
    this.classroomRepository = classroomRepository;
    this.userMapper = userMapper;
  }

  /**
   * Retrieves all users in the system.
   *
   * @return successful response containing all users mapped to response DTOs
   */
  @Transactional(readOnly = true)
  public ApiResponse<List<UserResponseDto>> getAllUsers() {
    List<UserResponseDto> users = userRepository.findAll().stream()
        .map(userMapper::toUserResponseDto)
        .toList();

    return ApiResponse.ok(users);
  }

  /**
   * Retrieves one user by id.
   *
   * @param id user id
   * @return successful response containing the matching user
   * @throws ResourceNotFoundException if no user exists for the provided id
   */
  @Transactional(readOnly = true)
  public ApiResponse<UserResponseDto> getUserById(Long id) {
    User user = userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

    return ApiResponse.ok(userMapper.toUserResponseDto(user));
  }

  /**
   * Creates a new student.
   *
   * @param request student creation payload
   * @return successful response containing the created student
   * @throws BadRequestException if a student with the same Feide username already exists
   * @throws ResourceNotFoundException if the provided classroom id does not exist
   */
  public ApiResponse<StudentResponseDto> createStudent(StudentRequestDto request) {
    studentRepository.findByFeideUsername(request.feideUsername())
        .ifPresent(student -> {
          throw new BadRequestException("Student already exists: " + request.feideUsername());
        });

    Classroom classroom = null;
    if (request.classroomId() != null) {
      classroom = classroomRepository.findById(request.classroomId())
          .orElseThrow(() -> new ResourceNotFoundException("Classroom not found: " + request.classroomId()));
    }

    Student savedStudent = studentRepository.save(userMapper.toStudentEntity(request, classroom));
    return ApiResponse.ok("Student created", userMapper.toStudentResponseDto(savedStudent));
  }

  /**
   * Creates a new teacher.
   *
   * @param request teacher creation payload
   * @return successful response containing the created teacher
   * @throws BadRequestException if a teacher with the same school email already exists
   */
  public ApiResponse<TeacherResponseDto> createTeacher(TeacherRequestDto request) {
    teacherRepository.findBySchoolEmail(request.schoolEmail())
        .ifPresent(teacher -> {
          throw new BadRequestException("Teacher already exists: " + request.schoolEmail());
        });

    Teacher savedTeacher = teacherRepository.save(userMapper.toTeacherEntity(request));
    return ApiResponse.ok("Teacher created", userMapper.toTeacherResponseDto(savedTeacher));
  }

  /**
   * Updates an existing student.
   *
   * @param id student id
   * @param request student update payload with optional fields
   * @return successful response containing the updated student
   * @throws ResourceNotFoundException if the student does not exist
   * @throws ResourceNotFoundException if a provided classroom id does not exist
   */
  public ApiResponse<StudentResponseDto> updateStudent(Long id, StudentUpdateDto request) {
    Student student = studentRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

    Classroom classroom = student.getClassroom();
    if (request.classroomId() != null) {
      classroom = classroomRepository.findById(request.classroomId())
          .orElseThrow(() -> new ResourceNotFoundException("Classroom not found: " + request.classroomId()));
    }

    userMapper.updateStudentEntity(student, request, classroom);

    Student savedStudent = studentRepository.save(student);
    return ApiResponse.ok("Student updated", userMapper.toStudentResponseDto(savedStudent));
  }

  /**
   * Updates an existing teacher.
   *
   * @param id teacher id
   * @param request teacher update payload with optional fields
   * @return successful response containing the updated teacher
   * @throws ResourceNotFoundException if the teacher does not exist
   */
  public ApiResponse<TeacherResponseDto> updateTeacher(Long id, TeacherUpdateDto request) {
    Teacher teacher = teacherRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + id));

    userMapper.updateTeacherEntity(teacher, request);

    Teacher savedTeacher = teacherRepository.save(teacher);
    return ApiResponse.ok("Teacher updated", userMapper.toTeacherResponseDto(savedTeacher));
  }


}
