package pl.edu.uj.gotowanko.exceptions.businesslogic;

import org.springframework.http.HttpStatus;

/**
 * Created by michal on 18.04.15.
 */
public class InvalidIngredientAmount extends BusinessLogicException {

    public InvalidIngredientAmount(Double amount) {
        super(String.format("Ingredient amount can't be less or equal to 0. Got %f.", amount), HttpStatus.PRECONDITION_FAILED);
    }

}
