package ntnu.idi.idatt2106.pilt.features.user;

import ntnu.idi.idatt2106.pilt.features.classroom.Classroom;
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
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between user entities and user-related DTOs.
 */
@Component
public class UserMapper {

  /**
   * Maps a student creation request to a new student entity.
   *
   * @param request student creation payload
   * @param classroom resolved classroom entity, or {@code null} when unassigned
   * @return a new {@link Student} entity populated from the request
   */
  public Student toStudentEntity(StudentRequestDto request, Classroom classroom) {
		Student student = new Student();
		student.setFirstName(request.firstName());
		student.setLastName(request.lastName());
		student.setPassword(request.password());
		student.setLevel(request.level());
		student.setTotalScore(request.totalScore());
		student.setFeideUsername(request.feideUsername());
		student.setDisplayName(request.displayName());
		student.setClassroom(classroom);
		return student;
  }

				  /**
				   * Applies non-null fields from a student update payload to an existing entity.
				   *
				   * @param student target student entity to modify
				   * @param request student update payload with optional fields
				   * @param classroom resolved classroom entity used when classroom id is provided
				   */
  public void updateStudentEntity(Student student, StudentUpdateDto request, Classroom classroom) {
		if (request.firstName() != null) {
			student.setFirstName(request.firstName());
		}
		if (request.lastName() != null) {
			student.setLastName(request.lastName());
		}
		if (request.password() != null) {
			student.setPassword(request.password());
		}
		if (request.level() != null) {
			student.setLevel(request.level());
		}
		if (request.totalScore() != null) {
			student.setTotalScore(request.totalScore());
		}
		if (request.displayName() != null) {
			student.setDisplayName(request.displayName());
		}
		if (request.classroomId() != null) {
			student.setClassroom(classroom);
		}
  }

	/**
	 * Applies non-null fields from a teacher update payload to an existing entity.
	 *
	 * @param teacher target teacher entity to modify
	 * @param request teacher update payload with optional fields
	 */
	public void updateTeacherEntity(Teacher teacher, TeacherUpdateDto request) {
		if (request.firstName() != null) {
			teacher.setFirstName(request.firstName());
		}
		if (request.lastName() != null) {
			teacher.setLastName(request.lastName());
		}
		if (request.password() != null) {
			teacher.setPassword(request.password());
		}
		if (request.schoolEmail() != null) {
			teacher.setSchoolEmail(request.schoolEmail());
		}
	}

				  /**
				   * Maps a teacher creation request to a new teacher entity.
				   *
				   * @param request teacher creation payload
				   * @return a new {@link Teacher} entity populated from the request
				   */
  public Teacher toTeacherEntity(TeacherRequestDto request) {
		Teacher teacher = new Teacher();
		teacher.setFirstName(request.firstName());
		teacher.setLastName(request.lastName());
		teacher.setPassword(request.password());
		teacher.setSchoolEmail(request.schoolEmail());
		return teacher;
  }

				  /**
				   * Maps a generic user entity to a base user response DTO.
				   *
				   * @param user source user entity
				   * @return mapped user response DTO
				   */
  public UserResponseDto toUserResponseDto(User user) {
		return new UserResponseDto(
			user.getId(),
			user.getFirstName(),
			user.getLastName(),
			user.getRole()
		);
  }

				  /**
				   * Maps a student entity to a student response DTO.
				   *
				   * @param student source student entity
				   * @return mapped student response DTO
				   */
  public StudentResponseDto toStudentResponseDto(Student student) {
		return new StudentResponseDto(
			student.getId(),
			student.getFirstName(),
			student.getLastName(),
			student.getRole(),
			student.getLevel(),
			student.getTotalScore(),
			student.getFeideUsername(),
			student.getDisplayName(),
			student.getClassroom() != null ? student.getClassroom().getId() : null
		);
  }

				  /**
				   * Maps a teacher entity to a teacher response DTO.
				   *
				   * @param teacher source teacher entity
				   * @return mapped teacher response DTO
				   */
  public TeacherResponseDto toTeacherResponseDto(Teacher teacher) {
		return new TeacherResponseDto(
			teacher.getId(),
			teacher.getFirstName(),
			teacher.getLastName(),
			teacher.getRole(),
			teacher.getSchoolEmail()
		);
  }

}
