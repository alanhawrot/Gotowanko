package pl.edu.uj.gotowanko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.gotowanko.entities.Ingredient;
import pl.edu.uj.gotowanko.entities.Recipe;

/**
 * Created by michal on 18.04.15.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
