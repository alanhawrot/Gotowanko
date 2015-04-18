package pl.edu.uj.gotowanko.controllers.recipes.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by michal on 17.04.15.
 */
public class CreateRecipeIngredientRequestDTO {

    @NotNull
    private Long ingredientId;

    @NotNull
    private Long ingredientUnitId;

    @NotNull
    private Long ingredientAmount;

    public Long getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(Long ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Long getIngredientUnitId() {
        return ingredientUnitId;
    }

    public void setIngredientUnitId(Long ingredientUnitId) {
        this.ingredientUnitId = ingredientUnitId;
    }
}
