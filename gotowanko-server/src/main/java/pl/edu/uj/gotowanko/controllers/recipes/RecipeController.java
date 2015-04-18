package pl.edu.uj.gotowanko.controllers.recipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.edu.uj.gotowanko.controllers.recipes.dto.CreateRecipeIngredientRequestDTO;
import pl.edu.uj.gotowanko.controllers.recipes.dto.CreateRecipeRequestDTO;
import pl.edu.uj.gotowanko.controllers.recipes.dto.CreateRecipeStepRequestDTO;
import pl.edu.uj.gotowanko.entities.Recipe;
import pl.edu.uj.gotowanko.exceptions.businesslogic.InvalidIngredient;
import pl.edu.uj.gotowanko.exceptions.businesslogic.InvalidIngredientAmount;
import pl.edu.uj.gotowanko.exceptions.businesslogic.InvalidIngredientUnit;
import pl.edu.uj.gotowanko.repositories.RecipesRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by michal on 17.04.15.
 */
@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {
    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class.getSimpleName());

    @Autowired
    private RecipesRepository recipesRepository;

    @Autowired
    private RecipeFactory recipeFactory;

    @Secured(value = "ROLE_USER")
    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecipe(@Valid @RequestBody CreateRecipeRequestDTO dto)
            throws InvalidIngredientUnit, InvalidIngredientAmount, InvalidIngredient {

        RecipeBuilder recipeBuilder = recipeFactory.builderForRecipe()
                .withApproximateCost(dto.getApproximateCost())
                .withCookingTimeInMinutes(dto.getCookingTimeInMinutes())
                .withTitle(dto.getTitle())
                .withPhotoUrl(dto.getPhotoUrl());
        //TODO: withRating(?)
        for (CreateRecipeStepRequestDTO stepDto : dto.getRecipeSteps()) {
            RecipeStepBuilder recipeStepBuilder = recipeFactory.builderForRecipeStep()
                    .withTitle(stepDto.getTitle())
                    .withDescription(stepDto.getDescription())
                    .withPhotoUrl(stepDto.getPhotoUrl())
                    .withVideoUrl(stepDto.getVideoUrl())
                    .withRealizationTime(stepDto.getRealizationTime())
                    .withTimerDurationInMinutes(stepDto.getTimerDurationInMinutes());

            for (CreateRecipeIngredientRequestDTO ingredientDto : stepDto.getIngredients()) {
                recipeStepBuilder.withIngredient(
                        recipeFactory.builderForIngredientAmount()
                                .withAmount(ingredientDto.getIngredientAmount())
                                .withIngredient(ingredientDto.getIngredientUnitId())
                                .withIngredientUnit(ingredientDto.getIngredientUnitId())
                                .build());
            }
            recipeBuilder.withRecipeStep(recipeStepBuilder.build());
        }

        Recipe recipe = recipeBuilder.build();
        recipesRepository.save(recipe);
    }
}
