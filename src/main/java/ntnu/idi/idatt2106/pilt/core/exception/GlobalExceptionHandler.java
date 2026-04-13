package ntnu.idi.idatt2106.pilt.core.exception;

import ntnu.idi.idatt2106.pilt.core.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles all predefined ApiExceptions (400, 401, 403, etc.) and returns a structured error response.
   */
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
    log.warn("API Error: {} - {}", ex.getStatus(), ex.getMessage());
    return ResponseEntity
        .status(ex.getStatus())
        .body(ApiResponse.error(ex.getMessage()));
  }

  /**
   * Handles unexpected errors (500 Internal Server Error).
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex, WebRequest request) {
    log.error("Unexpected error occurred: ", ex);
    return ResponseEntity
        .status(500)
        .body(ApiResponse.error("An unexpected error occurred. Please try again later."));
  }
}