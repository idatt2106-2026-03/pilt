package ntnu.idi.idatt2106.pilt.core.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ApiException {
  public EntityNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
