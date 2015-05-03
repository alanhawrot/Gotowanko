package pl.edu.uj.gotowanko.controllers.recipes.builders;

import pl.edu.uj.gotowanko.controllers.users.UserService;
import pl.edu.uj.gotowanko.entities.Recipe;
import pl.edu.uj.gotowanko.entities.RecipeStep;
import pl.edu.uj.gotowanko.entities.User;

import java.time.Duration;
import java.util.Calendar;
import java.util.Optional;

/**
 * Created by michal on 18.04.15.
 */
public class RecipeBuilder {

    private Recipe recipe;

    public RecipeBuilder(UserService userService, Recipe recipe) {
        this.recipe = recipe;

        if (recipe.getDateAdded() == null)
            recipe.setDateAdded(Calendar.getInstance());

        if (recipe.getLastEdited() == null)
            recipe.setLastEdited(Calendar.getInstance());

        if (recipe.getUser() == null) {
            Optional<User> currentlyLoggedUser = userService.getCurrentlyLoggedUser();
            if (currentlyLoggedUser.isPresent())
                recipe.setUser(currentlyLoggedUser.get());
        }

        if(recipe.getState() == null)
            recipe.setState(Recipe.RecipeState.NORMAL);
    }

    public RecipeBuilder(UserService userService) {
        this(userService, new Recipe());
    }

    public RecipeBuilder withApproximateCost(Integer approximateCost) {
        recipe.setApproximateCost(approximateCost);
        return this;
    }

    public RecipeBuilder withCookingTimeInMinutes(Duration cookingTimeInMinutes) {
        if (cookingTimeInMinutes != null)
            recipe.setCookingTimeInMinutes((int) cookingTimeInMinutes.toMinutes());
        return this;
    }

    public RecipeBuilder withTitle(String title) {
        recipe.setTitle(title);
        return this;
    }

    public RecipeBuilder withPhotoUrl(String photoUrl) {
        recipe.setPhotoUrl(photoUrl);
        return this;
    }

    public RecipeBuilder withRecipeStep(RecipeStep recipeStep) {
        recipe.addRecipeStep(recipeStep);
        recipeStep.setRecipe(recipe);
        return this;
    }

    public RecipeBuilder withLastEdited(Calendar instance) {
        recipe.setLastEdited(instance);
        return this;
    }

    public RecipeBuilder withRecipeState(Recipe.RecipeState updateProposition) {
        recipe.setState(updateProposition);
        return this;
    }

    public Recipe build() {
        if (recipe == null)
            throw new IllegalStateException("RecipeBuilder may not be reused");
        if (recipe.getTitle() == null)
            throw new IllegalStateException("Title must be set before building recipe");
        if (recipe.getUser() == null)
            throw new IllegalStateException("User must be set before building recipe");
        if (recipe.getLastEdited() == null)
            throw new IllegalStateException("Last edited must be set before building recipe");
        if (recipe.getDateAdded() == null)
            throw new IllegalStateException("Date added must be set before building recipe");
        if (recipe.getRecipeSteps() == null || recipe.getRecipeSteps().isEmpty())
            throw new IllegalStateException("At least one recipe step must be set before building recipe");
        if(recipe.getState() == null)
            throw  new IllegalStateException("Recipe state must be set before building recipe");

        Recipe result = this.recipe;
        this.recipe = null;
        return result;
    }

}
