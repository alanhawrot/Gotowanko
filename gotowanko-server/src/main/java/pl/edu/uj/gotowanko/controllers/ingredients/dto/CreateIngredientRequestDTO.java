package pl.edu.uj.gotowanko.controllers.ingredients.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

/**
 * Created by michal on 19.04.15.
 */
public class CreateIngredientRequestDTO {
    @NotBlank
    private String name;

    private String iconUrl;


    private Set<Long> ingredientCategoryIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Set<Long> getIngredientCategoryIds() {
        return ingredientCategoryIds;
    }

    public void setIngredientCategoryIds(Set<Long> ingredientCategoryIds) {
        this.ingredientCategoryIds = ingredientCategoryIds;
    }
}
