package pl.edu.uj.gotowanko.controllers.ingredients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.edu.uj.gotowanko.controllers.ingredients.dto.CreateIngredientCategoryRequestDTO;
import pl.edu.uj.gotowanko.controllers.ingredients.dto.CreateIngredientCategoryResponseDTO;
import pl.edu.uj.gotowanko.controllers.ingredients.dto.CreateIngredientRequestDTO;
import pl.edu.uj.gotowanko.controllers.ingredients.dto.CreateIngredientResponseDTO;
import pl.edu.uj.gotowanko.entities.Ingredient;
import pl.edu.uj.gotowanko.entities.IngredientCategory;
import pl.edu.uj.gotowanko.exceptions.businesslogic.NoSuchResourceException;
import pl.edu.uj.gotowanko.repositories.IngredientCategoryRepository;
import pl.edu.uj.gotowanko.repositories.IngredientRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * Created by michal on 19.04.15.
 */

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Secured("ROLE_USER")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public CreateIngredientResponseDTO createIngredient(@Valid @RequestBody CreateIngredientRequestDTO dto) throws NoSuchResourceException {
        Ingredient ingredient = new Ingredient();
        ingredient.setIconUrl(dto.getIconUrl());
        ingredient.setName(dto.getIngredientName());
        ingredient = ingredientRepository.save(ingredient);

        if(dto.getIngredientCategoryIds() != null) {
            for(Long categoryId : dto.getIngredientCategoryIds()) {
                IngredientCategory ingredientCategory = ingredientCategoryRepository.findOne(categoryId);
                if(ingredientCategory == null)
                    throw new NoSuchResourceException("ingredient category with id %d doesn't exists", categoryId);
                ingredientCategory.addIngredient(ingredient);
                ingredientCategoryRepository.save(ingredientCategory);
            }
        }

        CreateIngredientResponseDTO responseDTO = new CreateIngredientResponseDTO();
        responseDTO.setIngredientId(ingredient.getId());
        return responseDTO;
    }

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public CreateIngredientCategoryResponseDTO createIngredientCategory(@Valid @RequestBody CreateIngredientCategoryRequestDTO dto) {
        IngredientCategory category = new IngredientCategory();
        category.setIconUrl(dto.getIconUrl());
        category.setName(dto.getIngredientCategoryName());
        category = ingredientCategoryRepository.save(category);

        CreateIngredientCategoryResponseDTO responseDTO = new CreateIngredientCategoryResponseDTO();
        responseDTO.setIngredientCategoryId(category.getId());
        return responseDTO;
    }
}
