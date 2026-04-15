package ntnu.idi.idatt2106.pilt.core.exception;

import jakarta.validation.ConstraintViolationException;
import ntnu.idi.idatt2106.pilt.core.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.stream.Collectors;

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
   * Handles @Valid failures on @RequestBody DTOs.
   * Returns 400 with a map of field names to validation error messages.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {

    Map<String, String> fieldErrors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            fe -> fe.getField(),
            fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value",
            (first, second) -> first
        ));

    log.warn("Validation failed: {}", fieldErrors);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error("Validation failed", fieldErrors));
  }

  /**
   * Handles malformed JSON or unreadable request bodies.
   * Returns 400 instead of letting it fall through to 500.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<Void>> handleUnreadableMessage(
      HttpMessageNotReadableException ex) {
    log.warn("Malformed request body: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error("Invalid request body"));
  }

  /**
   * Handles @Validated constraint violations on path variables and request parameters.
   * Returns 400 with details about which constraints were violated.
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(
      ConstraintViolationException ex) {

    Map<String, String> violations = ex.getConstraintViolations()
        .stream()
        .collect(Collectors.toMap(
            v -> {
              String path = v.getPropertyPath().toString();
              // Extract just the parameter name (last segment of the path)
              int lastDot = path.lastIndexOf('.');
              return lastDot >= 0 ? path.substring(lastDot + 1) : path;
            },
            v -> v.getMessage(),
            (first, second) -> first
        ));

    log.warn("Constraint violations: {}", violations);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error("Validation failed", violations));
  }

  /**
   * Handles Spring Security access denied errors.
   * Returns 403 instead of letting it fall through to 500.
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
    log.warn("Access denied: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(ApiResponse.error("You do not have permission to perform this action"));
  }

  /**
   * Handles wrong HTTP method (e.g., POST to a GET-only endpoint).
   * Returns 405 instead of letting it fall through to 500.
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException ex) {
    log.warn("Method not allowed: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ApiResponse.error("HTTP method " + ex.getMethod() + " is not supported for this endpoint"));
  }

  /**
   * Handles unexpected errors (500 Internal Server Error).
   * This is the catch-all fallback — keep it last.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex, WebRequest request) {
    log.error("Unexpected error occurred: ", ex);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error("An unexpected error occurred. Please try again later."));
  }
}