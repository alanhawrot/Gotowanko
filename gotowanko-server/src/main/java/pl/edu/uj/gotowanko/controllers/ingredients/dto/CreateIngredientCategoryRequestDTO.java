package pl.edu.uj.gotowanko.controllers.ingredients.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by michal on 19.04.15.
 */
public class CreateIngredientCategoryRequestDTO {
    @NotBlank
    private String name;

    private String iconUrl;

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
}
