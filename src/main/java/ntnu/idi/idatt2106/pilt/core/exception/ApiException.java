package ntnu.idi.idatt2106.pilt.core.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
  private final HttpStatus status;

  protected ApiException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}