package pl.edu.uj.gotowanko.controllers.ingredients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.uj.gotowanko.controllers.ingredients.dto.GetAllIngredientUnitsItemResponseDTO;
import pl.edu.uj.gotowanko.controllers.ingredients.dto.GetAllIngredientUnitsListResponseDTO;
import pl.edu.uj.gotowanko.entities.IngredientUnit;
import pl.edu.uj.gotowanko.repositories.IngredientUnitRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by michal on 19.04.15.
 */

@RestController
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
            ingredientUnitRepository.save(new IngredientUnit("Kilogram", "kg"));
            ingredientUnitRepository.save(new IngredientUnit("Dekagram", "dg"));
            ingredientUnitRepository.save(new IngredientUnit("Gram", "g"));
            ingredientUnitRepository.save(new IngredientUnit("Litr", "l"));
            ingredientUnitRepository.save(new IngredientUnit("Mililitr", "ml"));
            ingredientUnitRepository.save(new IngredientUnit("Opakowanie", "opak"));
            ingredientUnitRepository.save(new IngredientUnit("Sztuka", "szt"));
        }
    }

    @Secured("ROLE_USER")
    @Transactional
    @RequestMapping(value = "/rest/ingredients/units", method = RequestMethod.GET)
    public GetAllIngredientUnitsListResponseDTO getAllIngredientUnits() {
        List<IngredientUnit> ingredientUnitList = ingredientUnitRepository.findAll();

        GetAllIngredientUnitsListResponseDTO getAllIngredientUnitsListResponseDTO = new GetAllIngredientUnitsListResponseDTO();

        ingredientUnitList.forEach(iu -> {
            GetAllIngredientUnitsItemResponseDTO ingredientUnitsItemResponseDTO = new GetAllIngredientUnitsItemResponseDTO();
            ingredientUnitsItemResponseDTO.setId(iu.getId());
            ingredientUnitsItemResponseDTO.setName(iu.getName());
            ingredientUnitsItemResponseDTO.setShortName(iu.getShortName());

            getAllIngredientUnitsListResponseDTO.getIngredientUnits().add(ingredientUnitsItemResponseDTO);
        });

        return getAllIngredientUnitsListResponseDTO;
    }
}
