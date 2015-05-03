package pl.edu.uj.gotowanko.controllers.recipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.edu.uj.gotowanko.controllers.recipes.builders.RecipeBuilder;
import pl.edu.uj.gotowanko.controllers.recipes.builders.RecipeStepBuilder;
import pl.edu.uj.gotowanko.controllers.recipes.dto.*;
import pl.edu.uj.gotowanko.controllers.users.UserService;
import pl.edu.uj.gotowanko.entities.*;
import pl.edu.uj.gotowanko.exceptions.businesslogic.*;
import pl.edu.uj.gotowanko.repositories.RecipesRepository;

import javax.validation.Valid;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

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

    @Autowired
    private UserService userService;

    @Secured(value = "ROLE_USER")
    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRecipeResponseDTO createRecipe(@Valid @RequestBody CreateRecipeRequestDTO dto)
            throws InvalidIngredientUnit, InvalidIngredientAmount, InvalidIngredient {

        RecipeBuilder recipeBuilder = recipeFactory.builderForRecipe()
                .withTitle(dto.getTitle())
                .withApproximateCost(dto.getApproximateCost())
                .withCookingTimeInMinutes(dto.getCookingTime())
                .withPhotoUrl(dto.getPhotoUrl());
        //TODO: withRating(?)
        for (CreateRecipeStepRequestDTO stepDto : dto.getRecipeSteps()) {
            RecipeStepBuilder recipeStepBuilder = recipeFactory.builderForRecipeStep()
                    .withTitle(stepDto.getTitle())
                    .withDescription(stepDto.getDescription())
                    .withPhotoUrl(stepDto.getPhotoUrl())
                    .withVideoUrl(stepDto.getVideoUrl())
                    .withRealizationTime(stepDto.getRealizationTime())
                    .withTimerDurationInMinutes(stepDto.getTimerDuration());

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
        recipe = recipesRepository.save(recipe);
        CreateRecipeResponseDTO responseDTO = new CreateRecipeResponseDTO();
        responseDTO.setRecipeId(recipe.getId());
        return responseDTO;
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GetRecipeResponseDTO getRecipe(@PathVariable Long id) throws NoSuchResourceException {
        Recipe recipe = recipesRepository.findOne(id);
        if (recipe == null)
            throw new NoSuchResourceException("There is no recipe with given id");

        GetRecipeResponseDTO dto = new GetRecipeResponseDTO();
        dto.setTitle(recipe.getTitle());
        dto.setDateAdded(recipe.getDateAdded());
        dto.setLastEdited(recipe.getLastEdited());
        dto.setApproximateCost(recipe.getApproximateCost());
        dto.setLikesNumber(recipe.getUserLikes().size());
        dto.setCookingTime(Duration.of(recipe.getCookingTimeInMinutes(), ChronoUnit.MINUTES));
        dto.setPhotoUrl(recipe.getPhotoUrl());

        //TODO: recipe.getUser().getName()
        for (RecipeStep recipeStep : recipe.getRecipeSteps()) {
            GetRecipeStepsResponseDTO recipeStepDto = new GetRecipeStepsResponseDTO();
            recipeStepDto.setTitle(recipeStep.getTitle());
            recipeStepDto.setDescription(recipeStep.getDescription());
            for (IngredientAmount ingredientAmount : recipeStep.getIngredients()) {
                GetRecipeStepIngredientResponseDTO recipeStepIngredientDto = new GetRecipeStepIngredientResponseDTO();
                recipeStepIngredientDto.setId(ingredientAmount.getIngredient().getId());
                recipeStepIngredientDto.setName(ingredientAmount.getIngredient().getName());
                recipeStepIngredientDto.setAmount(ingredientAmount.getAmount());
                recipeStepIngredientDto.setIconUrl(ingredientAmount.getIngredient().getIconUrl());
                recipeStepIngredientDto.setUnitName(ingredientAmount.getIngredientUnit().getName());
                recipeStepIngredientDto.setUnitShortName(ingredientAmount.getIngredientUnit().getShortName());
                recipeStepIngredientDto.setUnitId(ingredientAmount.getIngredientUnit().getId());

                recipeStepDto.getIngredients().add(recipeStepIngredientDto);
            }
            recipeStepDto.setPhotoUrl(recipeStep.getPhotoUrl());
            recipeStepDto.setRealizationTime(Duration.of(recipeStep.getRealizationTimeInMinutes(), ChronoUnit.MINUTES));
            recipeStepDto.setTimerDuration(Duration.of(recipeStep.getTimerDurationInMinutes(), ChronoUnit.MINUTES));
            recipeStepDto.setVideoUrl(recipeStep.getVideoUrl());

            dto.getRecipeSteps().add(recipeStepDto);
        }

        for (Comment comment : recipe.getComments()) {
            //TODO: comment.getUser().getName();
            GetRecipeCommentResponseDTO commentDto = new GetRecipeCommentResponseDTO();
            commentDto.setLastEdited(comment.getLastEdited());
            commentDto.setContent(comment.getContent());
            commentDto.setId(comment.getId());

            dto.getComments().add(commentDto);
        }

        return dto;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id}/liked", method = RequestMethod.GET)
    public LikedRecipeResponseDTO likeRecipe(@PathVariable Long id) throws NoSuchResourceException, RecipeAlreadyLikedException {
        Recipe recipe = recipesRepository.findOne(id);

        if (recipe == null) {
            throw new NoSuchResourceException("There is no recipe with given id");
        }

        User user = userService.getCurrentlyLoggedUser().get();

        if (user.containsRecipeLike(recipe)) {
            throw new RecipeAlreadyLikedException("Given user has already liked given recipe");
        }

        recipe.addUserLike(user);
        user.addRecipeLike(recipe);

        LikedRecipeResponseDTO likedRecipe = new LikedRecipeResponseDTO();
        likedRecipe.setId(recipe.getId());
        likedRecipe.setTitle(recipe.getTitle());
        likedRecipe.setPhotoUrl(recipe.getPhotoUrl());
        likedRecipe.setCookingTimeInMinutes(recipe.getCookingTimeInMinutes());
        likedRecipe.setApproximateCost(recipe.getApproximateCost());
        likedRecipe.setDateAdded(recipe.getDateAdded());
        likedRecipe.setLastEdited(recipe.getLastEdited());
        likedRecipe.setRecipeSteps(recipe.getRecipeSteps());
        likedRecipe.setUser(recipe.getUser());
        likedRecipe.setComments(recipe.getComments());
        likedRecipe.setUserLikes(recipe.getUserLikes());

        return likedRecipe;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id}/disliked", method = RequestMethod.GET)
    public DislikedRecipeResponseDTO dislikeRecipe(@PathVariable Long id) throws NoSuchResourceException, RecipeAlreadyDislikedException {
        Recipe recipe = recipesRepository.findOne(id);

        if (recipe == null) {
            throw new NoSuchResourceException("There is no recipe with given id");
        }

        User user = userService.getCurrentlyLoggedUser().get();

        if (!user.containsRecipeLike(recipe)) {
            throw new RecipeAlreadyDislikedException("Given user has already disliked given recipe");
        }

        recipe.removeUserLike(user);
        user.removeRecipeLike(recipe);

        DislikedRecipeResponseDTO dislikedRecipe = new DislikedRecipeResponseDTO();
        dislikedRecipe.setId(recipe.getId());
        dislikedRecipe.setTitle(recipe.getTitle());
        dislikedRecipe.setPhotoUrl(recipe.getPhotoUrl());
        dislikedRecipe.setCookingTimeInMinutes(recipe.getCookingTimeInMinutes());
        dislikedRecipe.setApproximateCost(recipe.getApproximateCost());
        dislikedRecipe.setDateAdded(recipe.getDateAdded());
        dislikedRecipe.setLastEdited(recipe.getLastEdited());
        dislikedRecipe.setRecipeSteps(recipe.getRecipeSteps());
        dislikedRecipe.setUser(recipe.getUser());
        dislikedRecipe.setComments(recipe.getComments());
        dislikedRecipe.setUserLikes(recipe.getUserLikes());

        return dislikedRecipe;
    }
}
