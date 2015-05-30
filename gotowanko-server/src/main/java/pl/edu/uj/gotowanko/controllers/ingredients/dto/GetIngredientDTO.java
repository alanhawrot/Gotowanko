package pl.edu.uj.gotowanko.controllers.ingredients.dto;

import pl.edu.uj.gotowanko.entities.Ingredient;

/**
 * Created by michal on 30.05.15.
 */
public class GetIngredientDTO {

    private final Long id;
    private final String name;
    private final String iconUrl;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public GetIngredientDTO(Ingredient ingredient) {
        id = ingredient.getId();
        name = ingredient.getName();
        iconUrl = ingredient.getIconUrl();
    }
}
