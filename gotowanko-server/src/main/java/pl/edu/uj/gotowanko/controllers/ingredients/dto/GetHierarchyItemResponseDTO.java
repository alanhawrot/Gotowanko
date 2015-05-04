package pl.edu.uj.gotowanko.controllers.ingredients.dto;

import pl.edu.uj.gotowanko.entities.Ingredient;
import pl.edu.uj.gotowanko.entities.IngredientCategory;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by michal on 04.05.15.
 */
public class GetHierarchyItemResponseDTO {

    private Long id;
    private String name;
    private String iconUrl;
    private Collection<GetHierarchyItemResponseDTO> ingredients;

    public GetHierarchyItemResponseDTO(Ingredient ingredient) {
        id = ingredient.getId();
        name = ingredient.getName();
        iconUrl = ingredient.getIconUrl();
        if (ingredient instanceof IngredientCategory) {
            IngredientCategory ic = (IngredientCategory) ingredient;
            ingredients = ic.getIngredients()
                    .stream()
                    .map(subIngredient -> new GetHierarchyItemResponseDTO(subIngredient))
                    .collect(Collectors.toList());
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Collection<GetHierarchyItemResponseDTO> getIngredients() {
        return ingredients;
    }
}
