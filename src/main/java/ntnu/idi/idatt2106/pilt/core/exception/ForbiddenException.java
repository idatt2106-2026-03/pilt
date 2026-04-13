package ntnu.idi.idatt2106.pilt.core.exception;
import org.springframework.http.HttpStatus;

// 403 Forbidden
public class ForbiddenException extends ApiException {
  public ForbiddenException(String message) {
    super(message, HttpStatus.FORBIDDEN);
  }

}
