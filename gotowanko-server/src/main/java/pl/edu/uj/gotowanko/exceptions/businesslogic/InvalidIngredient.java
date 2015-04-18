package pl.edu.uj.gotowanko.exceptions.businesslogic;

import org.springframework.http.HttpStatus;

/**
 * Created by michal on 18.04.15.
 */
public class InvalidIngredient extends BusinessLogicException {
    public InvalidIngredient(Long ingredientId) {
        super(String.format("Ingredient with id %d doesn't exists", ingredientId), HttpStatus.PRECONDITION_FAILED);
    }
}
