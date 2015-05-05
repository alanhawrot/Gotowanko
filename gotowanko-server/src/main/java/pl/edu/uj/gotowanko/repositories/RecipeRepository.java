package pl.edu.uj.gotowanko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.gotowanko.entities.Ingredient;
import pl.edu.uj.gotowanko.entities.IngredientAmount;
import pl.edu.uj.gotowanko.entities.Recipe;
import pl.edu.uj.gotowanko.entities.RecipeStep;

import java.util.List;

/**
 * Created by michal on 18.04.15.
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByTitleContainingIgnoreCaseOrUser_EmailContainingIgnoreCaseOrUser_NameContainingIgnoreCaseAndState(String title,
                                                                                                                        String userEmail,
                                                                                                                        String userName,
                                                                                                                        Recipe.RecipeState state);
}
