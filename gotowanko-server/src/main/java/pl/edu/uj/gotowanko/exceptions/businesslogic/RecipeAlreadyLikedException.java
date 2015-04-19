package pl.edu.uj.gotowanko.exceptions.businesslogic;

import org.springframework.http.HttpStatus;

/**
 * Created by alanhawrot on 19.04.15.
 */
public class RecipeAlreadyLikedException extends BusinessLogicException {

    public RecipeAlreadyLikedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
