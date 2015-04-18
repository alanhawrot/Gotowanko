package pl.edu.uj.gotowanko.exceptions.businesslogic;

import org.springframework.http.HttpStatus;

/**
 * Created by michal on 18.04.15.
 */
public class InvalidIngredientUnit extends BusinessLogicException {
    public InvalidIngredientUnit(Long unitId) {
        super(String.format("Unit with id %id doesn't exists", unitId), HttpStatus.PRECONDITION_FAILED);
    }
}
