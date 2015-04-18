package pl.edu.uj.gotowanko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.gotowanko.entities.IngredientAmount;
import pl.edu.uj.gotowanko.entities.Recipe;

/**
 * Created by michal on 18.04.15.
 */
public interface IngredientAmountRepository extends JpaRepository<IngredientAmount, Long> {
}
