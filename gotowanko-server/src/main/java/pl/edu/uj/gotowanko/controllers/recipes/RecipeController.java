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
import pl.edu.uj.gotowanko.repositories.*;
import pl.edu.uj.gotowanko.util.MailService;
import pl.edu.uj.gotowanko.util.PathService;

import javax.validation.Valid;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;

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
    private IngredientAmountRepository ingredientAmountRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeStepsRepository recipeStepsRepository;

    @Autowired
    private RecipeUpdatePropositionRepository recipeUpdatePropositionRepository;

    @Autowired
    private RecipeFactory recipeFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private PathService pathService;

    @Secured(value = "ROLE_USER")
    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRecipeResponseDTO createRecipe(@Valid @RequestBody CreateRecipeRequestDTO dto)
            throws NoSuchIngredientUnit, InvalidIngredientAmount, NoSuchIngredient {

        RecipeBuilder recipeBuilder = recipeFactory.builderForRecipe()
                .withTitle(dto.getTitle())
                .withApproximateCost(dto.getApproximateCost())
                .withCookingTimeInMinutes(dto.getCookingTime())
                .withPhotoUrl(dto.getPhotoUrl());
        //TODO: withRating(?)
        for (CreateRecipeStepRequestDTO stepDto : dto.getRecipeSteps()) {
            RecipeStepBuilder recipeStepBuilder = recipeFactory.builderForRecipeStep()
                    .withTitle(stepDto.getTitle())
                    .withStepNumber(stepDto.getStepNumber())
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

        Recipe recipe = recipesRepository.save(recipeBuilder.build());
        CreateRecipeResponseDTO responseDTO = new CreateRecipeResponseDTO();
        responseDTO.setRecipeId(recipe.getId());
        return responseDTO;
    }

    @Secured(value = "ROLE_USER")
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateRecipeRequest(@Valid @RequestBody UpdateRecipeRequestDTO dto, @PathVariable Long id)
            throws NoSuchIngredientUnit, InvalidIngredientAmount, NoSuchIngredient, NoSuchResourceException, PermissionDeniedException {

        Recipe recipe = recipesRepository.findOne(id);

        if (recipe == null)
            throw new NoSuchResourceException("Recipe with such id doesn't exists");

        if (!userService.getCurrentlyLoggedUser().get().equals(recipe.getUser()))
            throw new PermissionDeniedException("Not an owner of the recipe");

        recipe.getRecipeSteps().forEach(recipeStepsRepository::delete);
        recipe.getRecipeSteps().clear();

        RecipeBuilder recipeBuilder = recipeFactory.builderForRecipe(recipe)
                .withLastEdited(Calendar.getInstance())
                .withTitle(dto.getTitle())
                .withApproximateCost(dto.getApproximateCost())
                .withCookingTimeInMinutes(dto.getCookingTime())
                .withPhotoUrl(dto.getPhotoUrl());
        //TODO: withRating(?)
        for (UpdateRecipeStepRequestDTO stepDto : dto.getRecipeSteps()) {
            RecipeStepBuilder recipeStepBuilder = recipeFactory.builderForRecipeStep()
                    .withTitle(stepDto.getTitle())
                    .withStepNumber(stepDto.getStepNumber())
                    .withDescription(stepDto.getDescription())
                    .withPhotoUrl(stepDto.getPhotoUrl())
                    .withVideoUrl(stepDto.getVideoUrl())
                    .withRealizationTime(stepDto.getRealizationTime())
                    .withTimerDurationInMinutes(stepDto.getTimerDuration());

            for (UpdateRecipeIngredientRequestDTO ingredientDto : stepDto.getIngredients()) {
                recipeStepBuilder.withIngredient(
                        recipeFactory.builderForIngredientAmount()
                                .withAmount(ingredientDto.getIngredientAmount())
                                .withIngredient(ingredientDto.getIngredientUnitId())
                                .withIngredientUnit(ingredientDto.getIngredientUnitId())
                                .build());
            }
            recipeBuilder.withRecipeStep(recipeStepBuilder.build());
        }

        recipe = recipesRepository.save(recipeBuilder.build());
    }

    @Secured(value = "ROLE_USER")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{id}/proposition", method = RequestMethod.POST)
    public RecipeUpdatePropositionResponseDTO updateProposition(@Valid @RequestBody UpdatePropositionRequestDTO dto, @PathVariable Long id)
            throws NoSuchIngredientUnit, InvalidIngredientAmount, NoSuchIngredient, NoSuchResourceException, PermissionDeniedException {

        Recipe currentRecipe = recipesRepository.findOne(id);

        if (currentRecipe == null)
            throw new NoSuchResourceException("Recipe with such id doesn't exists");

        User requestingUser = userService.getCurrentlyLoggedUser().get();
        User requestedUser = currentRecipe.getUser();
        if (requestingUser.equals(requestedUser))
            throw new PermissionDeniedException("Can't send update proposition for your own recipe");

        RecipeBuilder recipeBuilder = recipeFactory.builderForRecipe()
                .withRecipeState(Recipe.RecipeState.UPDATE_PROPOSITION)
                .withTitle(dto.getTitle())
                .withApproximateCost(dto.getApproximateCost())
                .withCookingTimeInMinutes(dto.getCookingTime())
                .withPhotoUrl(dto.getPhotoUrl());
        //TODO: withRating(?)
        for (UpdatePropositionRecipeStepRequestDTO stepDto : dto.getRecipeSteps()) {
            RecipeStepBuilder recipeStepBuilder = recipeFactory.builderForRecipeStep()
                    .withTitle(stepDto.getTitle())
                    .withStepNumber(stepDto.getStepNumber())
                    .withDescription(stepDto.getDescription())
                    .withPhotoUrl(stepDto.getPhotoUrl())
                    .withVideoUrl(stepDto.getVideoUrl())
                    .withRealizationTime(stepDto.getRealizationTime())
                    .withTimerDurationInMinutes(stepDto.getTimerDuration());

            for (UpdateRecipePropositionIngredientRequestDTO ingredientDto : stepDto.getIngredients()) {
                recipeStepBuilder.withIngredient(
                        recipeFactory.builderForIngredientAmount()
                                .withAmount(ingredientDto.getIngredientAmount())
                                .withIngredient(ingredientDto.getIngredientUnitId())
                                .withIngredientUnit(ingredientDto.getIngredientUnitId())
                                .build());
            }
            recipeBuilder.withRecipeStep(recipeStepBuilder.build());
        }

        Recipe updatedRecipe = recipesRepository.save(recipeBuilder.build());
        RecipeUpdateProposition proposition = new RecipeUpdateProposition();
        proposition.setCurrentRecipe(currentRecipe);
        proposition.setUpdatedRecipe(updatedRecipe);
        proposition = recipeUpdatePropositionRepository.save(proposition);

        mailService.from(mailService.TEAM_EMAIL)
                .withTitle("Someone asks you to update recipe `%s`", currentRecipe.getTitle())
                .nextLine("Hello %s,%n", requestedUser.getEmail()) //TODO: getName()?
                .nextLine("User %s would like to improve your recipe titled `%s`.", requestingUser.getEmail(), currentRecipe.getTitle())
                .nextLine("To check requested changes please click below link:")
                .nextLine("%s%n", pathService.getWebUpdatePropositionPath(proposition.getId()))
                .nextLine("If you want to ignore this change request please let us know why:")
                .nextLine("%s", pathService.getWebRejectPropositionPath(proposition.getId()))
                .withGotowankoDefaultFooter()
                .send(requestedUser.getEmail());

        RecipeUpdatePropositionResponseDTO responseDTO = new RecipeUpdatePropositionResponseDTO();
        responseDTO.setRecipeUpdatePropositionId(proposition.getId());
        return responseDTO;
    }

    @Secured(value = "ROLE_USER")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{id}/proposition", method = RequestMethod.DELETE)
    public void cancelProposition(@Valid @RequestBody CancelPropositionRequestDTO dto , @PathVariable Long id) throws NoSuchResourceException, PermissionDeniedException {
        RecipeUpdateProposition proposition = recipeUpdatePropositionRepository.findOne(id);

        if (proposition == null)
            throw new NoSuchResourceException("Recipe update proposition with such id doesn't exists");

        User recipeOwner = proposition.getCurrentRecipe().getUser();
        User updateRequester = proposition.getUpdatedRecipe().getUser();
        User currentUser = userService.getCurrentlyLoggedUser().get();
        if (!Arrays.asList(recipeOwner, updateRequester).stream().anyMatch(currentUser::equals))
            throw new PermissionDeniedException("You doesn't have permission to cancel that proposition");

        if (recipeOwner.equals(currentUser)) {
            mailService.from(mailService.TEAM_EMAIL)
                    .withTitle("Update recipe request has been rejected")
                    .nextLine("Hello %s,%n", updateRequester.getEmail())
                    .nextLine("We are sorry, but %s has rejected update proposition to recipe `%s`", recipeOwner.getEmail(), proposition.getCurrentRecipe().getTitle())
                    .nextLine("We received fallowing reason:")
                    .nextLine("%s%n", dto.getReason())
                    .nextLine("Please write your own version of the recipe instead.")
                    .withGotowankoDefaultFooter()
                    .send(updateRequester.getEmail());
        }

        Recipe rejectedUpdate = proposition.getUpdatedRecipe();
        recipeUpdatePropositionRepository.delete(proposition);
        recipesRepository.delete(rejectedUpdate);


    }

    @Secured(value = "ROLE_USER")
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteRecipe(@PathVariable Long id) throws NoSuchResourceException, PermissionDeniedException {
        Recipe recipe = recipesRepository.findOne(id);
        if (recipe == null)
            throw new NoSuchResourceException("There is no recipe with given id");

        if (!userService.getCurrentlyLoggedUser().get().equals(recipe.getUser()))
            throw new PermissionDeniedException("Not an owner of the recipe");

        recipesRepository.delete(recipe);
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
