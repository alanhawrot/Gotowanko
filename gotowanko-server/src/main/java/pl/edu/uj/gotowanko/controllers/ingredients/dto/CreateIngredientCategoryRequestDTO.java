package pl.edu.uj.gotowanko.controllers.ingredients.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by michal on 19.04.15.
 */
public class CreateIngredientCategoryRequestDTO {

    @NotBlank
    private String ingredientCategoryName;

    private String iconUrl;

    public String getIngredientCategoryName() {
        return this.ingredientCategoryName;
    }

    public void setIngredientCategoryName(String ingredientCategoryName) {
        this.ingredientCategoryName = ingredientCategoryName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
