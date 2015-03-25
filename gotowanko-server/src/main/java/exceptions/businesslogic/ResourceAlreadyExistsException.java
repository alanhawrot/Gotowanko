package exceptions.businesslogic;

import org.springframework.http.HttpStatus;

/**
 * Created by michal on 23.03.15.
 */
public class ResourceAlreadyExistsException extends BusinessLogicException {
    public ResourceAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
