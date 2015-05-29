package pl.edu.uj.gotowanko.controllers.ingredients.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alanhawrot on 29.05.15.
 */
public class GetAllIngredientUnitsListResponseDTO {

    private List<GetAllIngredientUnitsItemResponseDTO> ingredientUnits = new ArrayList<>();

    public List<GetAllIngredientUnitsItemResponseDTO> getIngredientUnits() {
        return ingredientUnits;
    }

    public void setIngredientUnits(List<GetAllIngredientUnitsItemResponseDTO> ingredientUnits) {
        this.ingredientUnits = ingredientUnits;
    }
}
