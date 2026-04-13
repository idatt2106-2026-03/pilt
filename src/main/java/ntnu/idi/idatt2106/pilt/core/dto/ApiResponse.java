package ntnu.idi.idatt2106.pilt.core.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * Generic wrapper for API responses used by the backend.
 * <p>
 * Encapsulates whether the request was successful, an optional message and an
 * optional payload of type {@code T}. Fields with {@code null} values are
 * omitted from the JSON representation.
 *
 * @param <T> type of the response payload
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private final boolean success;
  private final String  message;
  private final T       data;

  private ApiResponse(boolean success, String message, T data) {
    this.success = success;
    this.message = message;
    this.data    = data;
  }

  /**
   * Creates a successful response with the given payload and no message.
   *
   * @param data response data
   * @return a successful {@code ApiResponse}
   * @param <T> type of the response payload
   */
  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(true, null, data);
  }

  /**
   * Creates a successful response with the given message and payload.
   *
   * @param message human-readable success message
   * @param data    response data
   * @return a successful {@code ApiResponse}
   * @param <T> type of the response payload
   */
  public static <T> ApiResponse<T> ok(String message, T data) {
    return new ApiResponse<>(true, message, data);
  }

  /**
   * Creates a successful response with only a message.
   *
   * @param message human-readable success message
   * @return a successful {@code ApiResponse} without data
   */
  public static ApiResponse<Void> ok(String message) {
    return new ApiResponse<>(true, message, null);
  }

  /**
   * Creates an error response with a message and no payload.
   *
   * @param message error message describing the failure
   * @return an error {@code ApiResponse} without data
   * @param <T> type of the (absent) response payload
   */
  public static <T> ApiResponse<T> error(String message) {
    return new ApiResponse<>(false, message, null);
  }

  /**
   * Creates an error response with a message and an optional payload
   * containing additional error context (for example field validation
   * details).
   *
   * @param message error message describing the failure
   * @param data    additional error details
   * @return an error {@code ApiResponse}
   * @param <T> type of the response payload
   */
  public static <T> ApiResponse<T> error(String message, T data) {
    return new ApiResponse<>(false, message, data);
  }

}