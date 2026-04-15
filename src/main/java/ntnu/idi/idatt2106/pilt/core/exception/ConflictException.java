package ntnu.idi.idatt2106.pilt.core.exception;

import org.springframework.http.HttpStatus;

/**
 * 409 Conflict — thrown when an operation would create a duplicate
 * or violate a uniqueness constraint.
 * <p>
 * Example: student tries to create a second notebook entry for the
 * same stoppested.
 */
public class ConflictException extends ApiException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
