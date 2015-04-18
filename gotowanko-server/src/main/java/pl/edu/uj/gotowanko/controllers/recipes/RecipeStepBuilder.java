package pl.edu.uj.gotowanko.controllers.recipes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edu.uj.gotowanko.entities.IngredientAmount;
import pl.edu.uj.gotowanko.entities.RecipeStep;

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
        recipeStep.setPhotoUrl(photoUrl);
        return this;
    }

    public RecipeStepBuilder withVideoUrl(String videoUrl) {
        recipeStep.setVideoUrl(videoUrl);
        return this;
    }

    public RecipeStepBuilder withRealizationTime(Duration realizationTime) {
        if (realizationTime != null)
            recipeStep.setRealizationTimeInMinutes((int) realizationTime.toMinutes());
        return this;
    }

    public RecipeStepBuilder withTimerDurationInMinutes(Duration remindAfterPeriod) {
        if (remindAfterPeriod != null)
            recipeStep.setTimerDurationInMinutes((int) remindAfterPeriod.toMinutes());
        return this;
    }

    public RecipeStepBuilder withIngredient(IngredientAmount ingredientAmount) {
        recipeStep.addIngredient(ingredientAmount);
        return this;
    }

    public RecipeStep build() {
        if (recipeStep == null)
            throw new IllegalStateException("RecipeStepBuilder may not be reused");
        if (recipeStep.getTitle() == null)
            throw new IllegalStateException("title must be set before building recipe step");
        if (recipeStep.getDescription() == null)
            throw new IllegalStateException("description must be set before building recipe step");
        if (recipeStep.getIngredients() == null || recipeStep.getIngredients().isEmpty())
            throw new IllegalStateException("atleast one ingredient must be set before building recipe step");

        RecipeStep result = this.recipeStep;
        this.recipeStep = null;
        return result;

    }
}
