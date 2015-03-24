package exceptions.businesslogic;

import org.springframework.http.HttpStatus;

/**
 * Created by michal on 23.03.15.
 */
public class PermissionDeniedException extends BusinessLogicException {
    public PermissionDeniedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
