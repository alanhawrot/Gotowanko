package pl.edu.uj.gotowanko.controllers.ingredients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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


/**
 * Created by michal on 19.04.15.
 */

@RestController
@RequestMapping(value = "/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public CreateIngredientResponseDTO createIngredient(CreateIngredientRequestDTO dto) throws NoSuchResourceException {
        Ingredient ingredient = new Ingredient();
        ingredient.setIconUrl(dto.getIconUrl());
        ingredient.setName(dto.getName());
        ingredient = ingredientRepository.save(ingredient);

        if(dto.getIngredientCategoryIds() != null) {
            for(Long categoryId : dto.getIngredientCategoryIds()) {
                IngredientCategory ingredientCategory = ingredientCategoryRepository.findOne(categoryId);
                if(ingredientCategory == null)
                    throw new NoSuchResourceException("ingredient category with id %d doesn't exists", categoryId);
                ingredientCategory.getIngredients().add(ingredient);
                ingredientCategoryRepository.save(ingredientCategory);
            }
        }

        CreateIngredientResponseDTO responseDTO = new CreateIngredientResponseDTO();
        responseDTO.setIngredientId(ingredient.getId());
        return responseDTO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public CreateIngredientCategoryResponseDTO createIngredientCategory(CreateIngredientCategoryRequestDTO dto) {
        IngredientCategory category = new IngredientCategory();
        category.setIconUrl(dto.getIconUrl());
        category.setName(dto.getName());
        category = ingredientRepository.save(category);


        CreateIngredientCategoryResponseDTO responseDTO = new CreateIngredientCategoryResponseDTO();
        responseDTO.setIngredientCategoryId(category.getId());
        return responseDTO;
    }
}
