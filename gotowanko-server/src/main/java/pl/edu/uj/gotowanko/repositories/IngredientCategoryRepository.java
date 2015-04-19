package pl.edu.uj.gotowanko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.gotowanko.entities.IngredientCategory;

/**
 * Created by michal on 19.04.15.
 */
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
}
