package pl.edu.uj.gotowanko.exceptions.businesslogic;

import org.springframework.http.HttpStatus;

/**
 * Created by michal on 01.06.15.
 */
public class IngredientsMayNotNotBeEmptyException extends BusinessLogicException {
    public IngredientsMayNotNotBeEmptyException() {
        super("at least one ingredient must be set before building recipe step", HttpStatus.PRECONDITION_FAILED);
    }
}
