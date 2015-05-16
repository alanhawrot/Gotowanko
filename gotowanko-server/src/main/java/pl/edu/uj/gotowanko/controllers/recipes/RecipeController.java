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
import pl.edu.uj.gotowanko.util.GetFilteredRecipesSortOptions;
import pl.edu.uj.gotowanko.util.MailService;
import pl.edu.uj.gotowanko.util.PathService;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by michal on 17.04.15.
 */
@RestController
@RequestMapping(value = "/rest/recipes")
public class RecipeController {
    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class.getSimpleName());

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientAmountRepository ingredientAmountRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeStepRepository recipeStepRepository;

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

    @Autowired
    private RecipeComparatorFactory recipeComparatorFactory;

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

        Recipe recipe = recipeRepository.save(recipeBuilder.build());
        CreateRecipeResponseDTO responseDTO = new CreateRecipeResponseDTO();
        responseDTO.setRecipeId(recipe.getId());
        return responseDTO;
    }

    @Secured(value = "ROLE_USER")
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateRecipeRequest(@Valid @RequestBody UpdateRecipeRequestDTO dto, @PathVariable Long id)
            throws NoSuchIngredientUnit, InvalidIngredientAmount, NoSuchIngredient, NoSuchResourceException, PermissionDeniedException {

        Recipe recipe = recipeRepository.findOne(id);

        if (recipe == null)
            throw new NoSuchResourceException("Recipe with such id doesn't exists");

        if (!userService.getCurrentlyLoggedUser().get().equals(recipe.getUser()))
            throw new PermissionDeniedException("Not an owner of the recipe");

        recipe.getRecipeSteps().forEach(recipeStepRepository::delete);
        recipe.getRecipeSteps().clear();

        RecipeBuilder recipeBuilder = recipeFactory.builderForRecipe(recipe)
                .withLastEdited(Calendar.getInstance())
                .withTitle(dto.getTitle())
                .withApproximateCost(dto.getApproximateCost())
                .withCookingTimeInMinutes(dto.getCookingTime())
                .withPhotoUrl(dto.getPhotoUrl());
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

        recipe = recipeRepository.save(recipeBuilder.build());
    }

    @Secured(value = "ROLE_USER")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{id}/proposition", method = RequestMethod.POST)
    public RecipeUpdatePropositionResponseDTO updateProposition(@Valid @RequestBody UpdatePropositionRequestDTO dto, @PathVariable Long id)
            throws NoSuchIngredientUnit, InvalidIngredientAmount, NoSuchIngredient, NoSuchResourceException, PermissionDeniedException {

        Recipe currentRecipe = recipeRepository.findOne(id);

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

        Recipe updatedRecipe = recipeRepository.save(recipeBuilder.build());
        RecipeUpdateProposition proposition = new RecipeUpdateProposition();
        proposition.setCurrentRecipe(currentRecipe);
        proposition.setUpdatedRecipe(updatedRecipe);
        proposition = recipeUpdatePropositionRepository.save(proposition);

        mailService.from(mailService.TEAM_EMAIL)
                .withTitle("Someone asks you to update recipe `%s`", currentRecipe.getTitle())
                .nextLine("Hello %s,%n", requestedUser.getName())
                .nextLine("User %s would like to improve your recipe titled `%s`.", requestingUser.getName(), currentRecipe.getTitle())
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
    @RequestMapping(value = "/proposition/{id}", method = RequestMethod.PUT)
    public void acceptProposition(@PathVariable Long id) throws NoSuchResourceException, PermissionDeniedException {
        RecipeUpdateProposition proposition = recipeUpdatePropositionRepository.findOne(id);

        if (proposition == null)
            throw new NoSuchResourceException("Recipe update proposition with such id doesn't exists");

        Recipe currentRecipe = proposition.getCurrentRecipe();
        Recipe recipeUpdate = proposition.getUpdatedRecipe();

        User recipeOwner = currentRecipe.getUser();
        User updateRequester = proposition.getUpdatedRecipe().getUser();
        User currentUser = userService.getCurrentlyLoggedUser().get();

        if (!currentUser.equals(recipeOwner))
            throw new PermissionDeniedException("You doesn't have permission to accept that proposition");

        mailService.from(mailService.TEAM_EMAIL)
                .withTitle("Update recipe request has been accepted")
                .nextLine("Hello %s,%n", updateRequester.getName())
                .nextLine("We are happy to let you know, that update proposition to recipe `%s` has been accepted by the owner.", currentRecipe.getTitle())
                .nextLine("Thank you for your contribution.")
                .withGotowankoDefaultFooter()
                .send(updateRequester.getEmail());

        currentRecipe.getRecipeSteps().forEach(recipeStepRepository::delete);
        currentRecipe.getRecipeSteps().clear();
        recipeRepository.save(
                recipeFactory.builderForRecipe(currentRecipe)
                        .withTitle(recipeUpdate.getTitle())
                        .withLastEdited(Calendar.getInstance())
                        .withPhotoUrl(recipeUpdate.getPhotoUrl())
                        .withApproximateCost(recipeUpdate.getApproximateCost())
                        .withCookingTimeInMinutes(Duration.of(recipeUpdate.getCookingTimeInMinutes(), ChronoUnit.MINUTES))
                        .withRecipeSteps(recipeUpdate.getRecipeSteps())
                        .build());
        recipeUpdatePropositionRepository.delete(proposition);
        recipeRepository.delete(recipeUpdate);

    }

    @Secured(value = "ROLE_USER")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/proposition/{id}", method = RequestMethod.DELETE)
    public void cancelProposition(@Valid @RequestBody CancelPropositionRequestDTO dto, @PathVariable Long id) throws NoSuchResourceException, PermissionDeniedException {
        RecipeUpdateProposition proposition = recipeUpdatePropositionRepository.findOne(id);

        if (proposition == null)
            throw new NoSuchResourceException("Recipe update proposition with such id doesn't exists");

        User recipeOwner = proposition.getCurrentRecipe().getUser();
        User updateRequester = proposition.getUpdatedRecipe().getUser();
        User currentUser = userService.getCurrentlyLoggedUser().get();
        if (!Arrays.asList(recipeOwner, updateRequester).stream().anyMatch(currentUser::equals))
            throw new PermissionDeniedException("You doesn't have permission to cancel that proposition");

        if (currentUser.equals(recipeOwner)) {
            mailService.from(mailService.TEAM_EMAIL)
                    .withTitle("Update recipe request has been rejected")
                    .nextLine("Hello %s,%n", updateRequester.getName())
                    .nextLine("We are sorry, but %s has rejected update proposition to recipe `%s`", recipeOwner.getName(), proposition.getCurrentRecipe().getTitle())
                    .nextLine("We received fallowing reason:")
                    .nextLine("%s%n", dto.getReason())
                    .nextLine("Please write your own version of the recipe instead.")
                    .withGotowankoDefaultFooter()
                    .send(updateRequester.getEmail());
        } else if (currentUser.equals(updateRequester)) {
            mailService.from(mailService.TEAM_EMAIL)
                    .withTitle("Update recipe request has been cancelled")
                    .nextLine("Hello %s,%n", updateRequester.getName())
                    .nextLine("%s has cancelled update request to recipe `%s`.", updateRequester.getName(), proposition.getCurrentRecipe().getTitle())
                    .nextLine("Link will be no longer available, so please ignore it.")
                    .withGotowankoDefaultFooter()
                    .send(recipeOwner.getEmail());
        }

        Recipe rejectedUpdate = proposition.getUpdatedRecipe();
        recipeUpdatePropositionRepository.delete(proposition);
        recipeRepository.delete(rejectedUpdate);


    }

    @Secured(value = "ROLE_USER")
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteRecipe(@PathVariable Long id) throws NoSuchResourceException, PermissionDeniedException {
        Recipe recipe = recipeRepository.findOne(id);
        if (recipe == null)
            throw new NoSuchResourceException("There is no recipe with given id");

        if (!userService.getCurrentlyLoggedUser().get().equals(recipe.getUser()))
            throw new PermissionDeniedException("Not an owner of the recipe");

        recipeRepository.delete(recipe);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GetRecipeResponseDTO getRecipe(@PathVariable Long id) throws NoSuchResourceException {
        Recipe recipe = recipeRepository.findOne(id);
        if (recipe == null)
            throw new NoSuchResourceException("There is no recipe with given id");

        GetRecipeResponseDTO dto = new GetRecipeResponseDTO();
        dto.setTitle(recipe.getTitle());
        dto.setDateAdded(recipe.getDateAdded());
        dto.setLastEdited(recipe.getLastEdited());
        dto.setApproximateCost(recipe.getApproximateCost());
        dto.setLikesNumber(recipe.getUserLikes().size());
        if (recipe.getCookingTimeInMinutes() != null)
            dto.setCookingTime(Duration.of(recipe.getCookingTimeInMinutes(), ChronoUnit.MINUTES));
        dto.setPhotoUrl(recipe.getPhotoUrl());
        dto.setUserName(recipe.getUser().getName());
        dto.setUserId(recipe.getUser().getId());

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
            if (recipeStep.getRealizationTimeInMinutes() != null)
                recipeStepDto.setRealizationTime(Duration.of(recipeStep.getRealizationTimeInMinutes(), ChronoUnit.MINUTES));
            if (recipeStep.getTimerDurationInMinutes() != null)
                recipeStepDto.setTimerDuration(Duration.of(recipeStep.getTimerDurationInMinutes(), ChronoUnit.MINUTES));
            recipeStepDto.setVideoUrl(recipeStep.getVideoUrl());

            dto.getRecipeSteps().add(recipeStepDto);
        }

        for (Comment comment : recipe.getComments()) {
            GetRecipeCommentResponseDTO commentDto = new GetRecipeCommentResponseDTO();
            commentDto.setUserId(comment.getUser().getId());
            commentDto.setUserName(comment.getUser().getName());
            commentDto.setLastEdited(comment.getLastEdited());
            commentDto.setContent(comment.getContent());
            commentDto.setId(comment.getId());

            dto.getComments().add(commentDto);
        }

        return dto;
    }

    @Secured("ROLE_USER")
    @Transactional
    @RequestMapping(value = "/{id}/liked", method = RequestMethod.GET)
    public void likeRecipe(@PathVariable Long id) throws NoSuchResourceException {
        Recipe recipe = recipeRepository.findOne(id);

        if (recipe == null) {
            throw new NoSuchResourceException("There is no recipe with given id");
        }

        User user = userService.getCurrentlyLoggedUser().get();

        if (!recipe.getUserLikes().contains(user)) {
            recipe.addUserLike(user);
            recipeRepository.save(recipe);
        }
    }

    @Secured("ROLE_USER")
    @Transactional
    @RequestMapping(value = "/{id}/disliked", method = RequestMethod.GET)
    public void dislikeRecipe(@PathVariable Long id) throws NoSuchResourceException {
        Recipe recipe = recipeRepository.findOne(id);

        if (recipe == null) {
            throw new NoSuchResourceException("There is no recipe with given id");
        }

        User user = userService.getCurrentlyLoggedUser().get();

        if (recipe.getUserLikes().contains(user)) {
            recipe.removeUserLike(user);
            recipeRepository.save(recipe);
        }
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public GetFilteredRecipesPageableResponseDTO searchRecipes(@RequestParam(value = "query", defaultValue = "") String query,
                                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                               @RequestParam(value = "sort", defaultValue = "BY_DATE_ADDED") String sort) throws UnsupportedEncodingException {
        if (size < 1) {
            size = 20;
        }

        GetFilteredRecipesSortOptions sortOption = GetFilteredRecipesSortOptions.BY_DATE_ADDED;
        GetFilteredRecipesSortOptions[] sortOptions = GetFilteredRecipesSortOptions.values();
        for (GetFilteredRecipesSortOptions so : sortOptions) {
            if (so.toString().compareToIgnoreCase(sort) == 0) {
                sortOption = so;
            }
        }

        String decodedQuery = URLDecoder.decode(query, "UTF-8");
        String[] keywords = decodedQuery.split("\\*");
        List<Recipe> filteredRecipes = new ArrayList<>();

        if (keywords.length > 0) {
            String firstKeyword = keywords[0].trim();

            filteredRecipes.addAll(recipeRepository.findByTitleContainingIgnoreCaseOrUser_EmailContainingIgnoreCaseOrUser_NameContainingIgnoreCaseAndState(
                    firstKeyword, firstKeyword, firstKeyword, Recipe.RecipeState.NORMAL));
            List<IngredientAmount> queriedIngredients = ingredientAmountRepository.findByIngredient_NameContainingIgnoreCase(firstKeyword);

            queriedIngredients.stream().map(IngredientAmount::getRecipeStep).map(RecipeStep::getRecipe).forEach(r -> {
                if (r.getState() == Recipe.RecipeState.NORMAL && !filteredRecipes.contains(r)) {
                    filteredRecipes.add(r);
                }
            });

            for (int i = 1; i < keywords.length; i++) {
                String keyword = keywords[i].toUpperCase().trim();

                filteredRecipes.stream().filter(r -> r.getTitle().toUpperCase().contains(keyword)
                        || r.getUser().getEmail().toUpperCase().contains(keyword)
                        || r.getUser().getName().toUpperCase().contains(keyword)
                        || r.getRecipeSteps().stream()
                        .anyMatch(rs -> rs.getIngredients().stream()
                                .anyMatch(ia -> ia.getIngredient().getName().toUpperCase().contains(keyword))));
            }
        }

        Collections.sort(filteredRecipes, recipeComparatorFactory.createRecipeComparator(sortOption));

        GetFilteredRecipesPageableResponseDTO getFilteredRecipesPageableResponseDTO = new GetFilteredRecipesPageableResponseDTO();

        GetFilteredRecipesPageableResponseDTO.PageMetadata pageMetadata = getFilteredRecipesPageableResponseDTO.createPageMetadata();
        pageMetadata.setSize(size);
        pageMetadata.setTotalElements(filteredRecipes.size());
        pageMetadata.setTotalPages(filteredRecipes.size() % size > 0 ? filteredRecipes.size() / size + 1 : filteredRecipes.size() / size);
        if (page > pageMetadata.getTotalPages()) {
            page = pageMetadata.getTotalPages();
        }
        if (page < 1) {
            page = 1;
        }
        pageMetadata.setNumber(page);

        getFilteredRecipesPageableResponseDTO.setPageMetadata(pageMetadata);

        for (int i = (page - 1) * size; i < size * page && i < pageMetadata.getTotalElements(); i++) {
            Recipe r = filteredRecipes.get(i);

            GetFilteredRecipesPageableResponseDTO.FilteredRecipeResponseDTO filteredRecipeResponseDTO = getFilteredRecipesPageableResponseDTO.createFilteredRecipeDTO();
            filteredRecipeResponseDTO.setId(r.getId());
            filteredRecipeResponseDTO.setTitle(r.getTitle());
            filteredRecipeResponseDTO.setUserId(r.getUser().getId());
            filteredRecipeResponseDTO.setUserEmail(r.getUser().getEmail());
            filteredRecipeResponseDTO.setUserName(r.getUser().getName());
            filteredRecipeResponseDTO.setNumberOfLikes(r.getUserLikes().size());
            filteredRecipeResponseDTO.setPhotoUrl(r.getPhotoUrl());
            filteredRecipeResponseDTO.setDateAdded(r.getDateAdded());
            filteredRecipeResponseDTO.setLastEdited(r.getLastEdited());

            getFilteredRecipesPageableResponseDTO.addFilteredRecipeDTO(filteredRecipeResponseDTO);
        }

        return getFilteredRecipesPageableResponseDTO;
    }

    @Secured("ROLE_USER")
    @Transactional
    @RequestMapping(value = "/{recipeId}/comments", method = RequestMethod.POST)
    public AddCommentResponseDTO addComment(@PathVariable Long recipeId, @Valid @RequestBody AddCommentRequestDTO addCommentRequestDTO) throws NoSuchResourceException {
        Recipe recipe = recipeRepository.findOne(recipeId);

        if (recipe == null) {
            throw new NoSuchResourceException("There is no recipe with given id");
        }

        User user = userService.getCurrentlyLoggedUser().get();

        Comment comment = new Comment();
        Calendar calendar = Calendar.getInstance();
        comment.setDateAdded(calendar);
        comment.setLastEdited(calendar);
        comment.setContent(addCommentRequestDTO.getContent());
        comment.setRecipe(recipe);
        comment.setUser(user);

        commentRepository.save(comment);

        AddCommentResponseDTO addCommentResponseDTO = new AddCommentResponseDTO();
        addCommentResponseDTO.setId(comment.getId());

        return addCommentResponseDTO;
    }

    @Secured("ROLE_USER")
    @Transactional
    @RequestMapping(value = "/{recipeId}/comments/{commentId}", method = RequestMethod.PUT)
    public void editComment(@PathVariable Long recipeId, @PathVariable Long commentId, @Valid @RequestBody AddCommentRequestDTO addCommentRequestDTO)
            throws NoSuchResourceException, PermissionDeniedException {
        Recipe recipe = recipeRepository.findOne(recipeId);

        if (recipe == null) {
            throw new NoSuchResourceException("There is no recipe with given id");
        }

        Comment comment = commentRepository.findOne(commentId);

        if (comment == null) {
            throw new NoSuchResourceException("There is no comment with given id");
        }

        User user = userService.getCurrentlyLoggedUser().get();

        if (!comment.getUser().equals(user)) {
            throw new PermissionDeniedException("You doesn't have permission to edit that comment");
        }

        comment.setContent(addCommentRequestDTO.getContent());
        commentRepository.save(comment);
    }

    @Secured("ROLE_USER")
    @Transactional
    @RequestMapping(value = "/{recipeId}/comments/{commentId}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable Long recipeId, @PathVariable Long commentId) throws NoSuchResourceException, PermissionDeniedException {
        Recipe recipe = recipeRepository.findOne(recipeId);

        if (recipe == null) {
            throw new NoSuchResourceException("There is no recipe with given id");
        }

        Comment comment = commentRepository.findOne(commentId);

        if (comment == null) {
            throw new NoSuchResourceException("There is no comment with given id");
        }

        User user = userService.getCurrentlyLoggedUser().get();

        if (!comment.getUser().equals(user)) {
            throw new PermissionDeniedException("You doesn't have permission to delete that comment");
        }

        commentRepository.delete(comment);
    }
}
