package pl.edu.uj.gotowanko.controllers.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edu.uj.gotowanko.controllers.users.UsersService;
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

    public RecipeBuilder(UsersService usersService, Recipe recipe) {
        this.recipe = recipe;

        if (recipe.getDateAdded() == null)
            recipe.setDateAdded(Calendar.getInstance());

        if (recipe.getLastEdited() == null)
            recipe.setLastEdited(Calendar.getInstance());

        if (recipe.getUser() == null) {
            Optional<User> currentlyLoggedUser = usersService.getCurrentlyLoggedUser();
            if (currentlyLoggedUser.isPresent())
                recipe.setUser(currentlyLoggedUser.get());
        }
    }

    public RecipeBuilder(UsersService usersService) {
        this(usersService, new Recipe());
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
            throw new IllegalStateException("Atleast one recipe step must be set before building recipe");

        Recipe result = this.recipe;
        this.recipe = null;
        return result;
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
        return this;
    }
}
