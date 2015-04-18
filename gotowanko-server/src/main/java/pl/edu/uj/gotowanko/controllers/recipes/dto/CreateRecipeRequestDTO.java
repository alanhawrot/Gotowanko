package pl.edu.uj.gotowanko.controllers.recipes.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by michal on 17.04.15.
 */
public class CreateRecipeRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    private List<CreateRecipeCardRequestDTO> cards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CreateRecipeCardRequestDTO> getCards() {
        return cards;
    }

    public void setCards(List<CreateRecipeCardRequestDTO> cards) {
        this.cards = cards;
    }
}
