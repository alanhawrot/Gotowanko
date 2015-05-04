package pl.edu.uj.gotowanko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.uj.gotowanko.entities.Ingredient;
import pl.edu.uj.gotowanko.entities.Recipe;

import java.util.Collection;

/**
 * Created by michal on 18.04.15.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT i FROM Ingredients i WHERE NOT EXISTS (SELECT ic FROM IngredientCategories ic JOIN ic.ingredients ings WHERE i IN ings)")
    Collection<? extends Ingredient> findRootIngredients();
}
