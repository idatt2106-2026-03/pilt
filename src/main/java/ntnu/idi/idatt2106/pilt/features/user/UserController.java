package ntnu.idi.idatt2106.pilt.features.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ntnu.idi.idatt2106.pilt.core.dto.ApiResponse;
import ntnu.idi.idatt2106.pilt.features.user.dto.StudentRequestDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.StudentResponseDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.StudentUpdateDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.TeacherRequestDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.TeacherResponseDto;
import ntnu.idi.idatt2106.pilt.features.user.dto.TeacherUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/teacher")
  public ResponseEntity<ApiResponse<TeacherResponseDto>> createTeacher(@Valid @RequestBody TeacherRequestDto request) {
    return ResponseEntity.ok(userService.createTeacher(request));
  }

  @PostMapping("/student")
  public ResponseEntity<ApiResponse<StudentResponseDto>> createStudent(@Valid @RequestBody StudentRequestDto request) {
    return ResponseEntity.ok(userService.createStudent(request));
  }

  @PatchMapping("/teacher/{id}")
  @PreAuthorize("hasRole('TEACHER')")
  public ResponseEntity<ApiResponse<TeacherResponseDto>> updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherUpdateDto request) {
    return ResponseEntity.ok(userService.updateTeacher(id, request));
  }

  @PatchMapping("/students/{id}")
  @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT')")
  public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentUpdateDto request) {
    return ResponseEntity.ok(userService.updateStudent(id, request));
  }
}
