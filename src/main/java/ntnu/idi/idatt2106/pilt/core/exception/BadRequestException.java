package ntnu.idi.idatt2106.pilt.core.exception;
import org.springframework.http.HttpStatus;

// 400 Bad Request
public class BadRequestException extends ApiException {
  public BadRequestException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }

}
