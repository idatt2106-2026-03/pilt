package ntnu.idi.idatt2106.pilt.features.classroom;

import ntnu.idi.idatt2106.pilt.features.classroom.dto.ClassroomRequestDto;
import ntnu.idi.idatt2106.pilt.features.classroom.dto.ClassroomResponseDto;
import org.springframework.stereotype.Component;

/**
 * Maps classroom request/response DTOs to and from {@link Classroom} entities.
 */
@Component
public class ClassroomMapper {

  /**
   * Creates a new classroom entity from a request DTO.
   *
   * @param request request payload containing classroom values
   * @return a new {@link Classroom} entity with mapped fields
   */
  public Classroom toEntity(ClassroomRequestDto request) {
    Classroom classroom = new Classroom();
    classroom.setName(request.name());
    classroom.setClassCode(request.classCode());
    return classroom;
  }

  /**
   * Applies request DTO values to an existing classroom entity.
   *
   * @param classroom existing classroom entity to update
   * @param request request payload containing updated values
   */
  public void updateEntity(Classroom classroom, ClassroomRequestDto request) {
    classroom.setName(request.name());
    classroom.setClassCode(request.classCode());
  }

  /**
   * Converts a classroom entity to a response DTO.
   *
   * @param classroom classroom entity
   * @return DTO containing public classroom response fields
   */
  public ClassroomResponseDto toResponseDto(Classroom classroom) {
    return new ClassroomResponseDto(
        classroom.getId(),
        classroom.getName(),
        classroom.getClassCode()
    );
  }
}

