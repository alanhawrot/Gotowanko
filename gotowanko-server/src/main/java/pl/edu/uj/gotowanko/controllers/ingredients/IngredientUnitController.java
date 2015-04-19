package pl.edu.uj.gotowanko.controllers.ingredients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.uj.gotowanko.entities.IngredientUnit;
import pl.edu.uj.gotowanko.repositories.IngredientUnitRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

/**
 * Created by michal on 19.04.15.
 */

@Controller
public class IngredientUnitController {

    @Autowired
    private IngredientUnitRepository ingredientUnitRepository;

    private static final Logger logger = LoggerFactory.getLogger(IngredientUnitController.class.getSimpleName());

    /**
     * Zahardcodowane jednostki
     */
    @PostConstruct
    @Transactional
    public void initializeWithDefaultUnits() {
        if (ingredientUnitRepository.findAll().isEmpty()) {
            logger.info("Initializing units");
            ingredientUnitRepository.save(new IngredientUnit("Kilogram", "Kg"));
            ingredientUnitRepository.save(new IngredientUnit("Dekagram", "Dg"));
            ingredientUnitRepository.save(new IngredientUnit("Gram", "g"));
            ingredientUnitRepository.save(new IngredientUnit("Litr", "l"));
        }
    }
}
