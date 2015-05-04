package pl.edu.uj.gotowanko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.gotowanko.entities.Recipe;
import pl.edu.uj.gotowanko.entities.RecipeStep;

/**
 * Created by michal on 18.04.15.
 */
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
}
