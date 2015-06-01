package pl.edu.uj.gotowanko.controllers.recipes.builders;

import pl.edu.uj.gotowanko.entities.IngredientAmount;
import pl.edu.uj.gotowanko.entities.RecipeStep;
import pl.edu.uj.gotowanko.exceptions.businesslogic.IngredientsMayNotNotBeEmptyException;

import java.time.Duration;

/**
 * Created by michal on 18.04.15.
 */
public class RecipeStepBuilder {

    private RecipeStep recipeStep;

    public RecipeStepBuilder(RecipeStep recipeStep) {
        this.recipeStep = recipeStep;
    }

    public RecipeStepBuilder() {
        this(new RecipeStep());
    }

    public RecipeStepBuilder withTitle(String title) {
        recipeStep.setTitle(title);
        return this;
    }

    public RecipeStepBuilder withDescription(String description) {
        recipeStep.setDescription(description);
        return this;
    }

    public RecipeStepBuilder withPhotoUrl(String photoUrl) {
        if (photoUrl != null && photoUrl.trim().isEmpty())
            recipeStep.setPhotoUrl(null);
        else
            recipeStep.setPhotoUrl(photoUrl);
        return this;
    }

    public RecipeStepBuilder withVideoUrl(String videoUrl) {
        if (videoUrl != null && videoUrl.trim().isEmpty())
            recipeStep.setVideoUrl(null);
        else
            recipeStep.setVideoUrl(videoUrl);
        return this;
    }

    public RecipeStepBuilder withRealizationTimeInMinutes(Duration realizationTime) {
        if (realizationTime != null)
            recipeStep.setRealizationTimeInMinutes((int) realizationTime.toMinutes());
        return this;
    }

    public RecipeStepBuilder withTimerDurationInSeconds(Duration remindAfterPeriod) {
        if (remindAfterPeriod != null)
            recipeStep.setTimerDurationInSeconds((int) remindAfterPeriod.toMillis() / 1000);
        return this;
    }

    public RecipeStepBuilder withIngredient(IngredientAmount ingredientAmount) {
        recipeStep.addIngredient(ingredientAmount);
        ingredientAmount.setRecipeStep(recipeStep);
        return this;
    }

    public RecipeStep build() throws IngredientsMayNotNotBeEmptyException {
        if (recipeStep == null)
            throw new IllegalStateException("RecipeStepBuilder may not be reused");
        if (recipeStep.getTitle() == null)
            throw new IllegalStateException("title must be set before building recipe step");
        if (recipeStep.getDescription() == null)
            throw new IllegalStateException("description must be set before building recipe step");
        if (recipeStep.getIngredients() == null || recipeStep.getIngredients().isEmpty())
            throw new IngredientsMayNotNotBeEmptyException();

        RecipeStep result = this.recipeStep;
        this.recipeStep = null;
        return result;

    }

    public RecipeStepBuilder withStepNumber(Long stepNumber) {
        this.recipeStep.setStepNumber(stepNumber);
        return this;
    }
}
