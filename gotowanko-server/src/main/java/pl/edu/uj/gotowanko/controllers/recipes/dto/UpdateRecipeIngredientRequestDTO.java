package pl.edu.uj.gotowanko.controllers.recipes.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by michal on 03.05.15.
 */
public class UpdateRecipeIngredientRequestDTO {
    @NotNull
    private Long ingredientUnitId;

    @NotNull
    private Long ingredientId;

    @Min(0)
    @NotNull
    private Double ingredientAmount;

    public Double getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(Double ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public Long getIngredientUnitId() {
        return ingredientUnitId;
    }

    public void setIngredientUnitId(Long ingredientUnitId) {
        this.ingredientUnitId = ingredientUnitId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}
