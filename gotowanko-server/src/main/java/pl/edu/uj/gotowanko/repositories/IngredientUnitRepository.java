package pl.edu.uj.gotowanko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.uj.gotowanko.entities.IngredientUnit;

/**
 * Created by michal on 18.04.15.
 */
public interface IngredientUnitRepository extends JpaRepository<IngredientUnit, Long> {
}
