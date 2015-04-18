package pl.edu.uj.gotowanko.controllers.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.uj.gotowanko.controllers.users.UsersService;
import pl.edu.uj.gotowanko.entities.IngredientAmount;
import pl.edu.uj.gotowanko.entities.Recipe;
import pl.edu.uj.gotowanko.entities.RecipeStep;
import pl.edu.uj.gotowanko.repositories.IngredientRepository;
import pl.edu.uj.gotowanko.repositories.IngredientUnitRepository;

/**
 * Created by michal on 18.04.15.
 */
@Component
public class RecipeFactory {

    @Autowired
    private UsersService usersService;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientUnitRepository ingredientUnitRepository;

    public RecipeBuilder builderForRecipe() {
        return new RecipeBuilder(usersService);
    }

    public RecipeBuilder builderForRecipe(Recipe recipe) {
        return new RecipeBuilder(usersService, recipe);
    }

    public RecipeStepBuilder builderForRecipeStep() {
        return new RecipeStepBuilder();
    }

    public RecipeStepBuilder builderForRecipeStep(RecipeStep recipeStep) {
        return new RecipeStepBuilder(recipeStep);
    }

    public IngredientAmountBuilder builderForIngredientAmount() {
        return new IngredientAmountBuilder(ingredientRepository, ingredientUnitRepository);
    }

    public IngredientAmountBuilder builderForIngredientAmount(IngredientAmount ingredientAmount) {
        return new IngredientAmountBuilder(ingredientRepository, ingredientUnitRepository, ingredientAmount);
    }
}
