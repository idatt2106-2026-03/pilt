package ntnu.idi.idatt2106.pilt.core.exception;
import org.springframework.http.HttpStatus;

// 404 Not Found
public class ResourceNotFoundException extends ApiException {
  public ResourceNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
