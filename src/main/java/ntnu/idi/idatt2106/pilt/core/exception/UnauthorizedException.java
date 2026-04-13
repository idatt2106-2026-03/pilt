package ntnu.idi.idatt2106.pilt.core.exception;
import org.springframework.http.HttpStatus;

// 401 Unauthorized
public class UnauthorizedException extends ApiException {
  public UnauthorizedException(String message) {
    super(message, HttpStatus.UNAUTHORIZED);
  }
}
