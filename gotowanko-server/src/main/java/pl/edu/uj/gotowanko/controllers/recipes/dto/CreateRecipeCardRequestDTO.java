package pl.edu.uj.gotowanko.controllers.recipes.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;

/**
 * Created by michal on 17.04.15.
 */
public class CreateRecipeCardRequestDTO {

    @NotNull
    private Long cardNumber;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Duration remindAfterPeriod;

    private Duration realization;

    @NotEmpty
    private List<CreateRecipeIngredientRequestDTO> ingredients;

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getRemindAfterPeriod() {
        return remindAfterPeriod;
    }

    public void setRemindAfterPeriod(Duration remindAfterPeriod) {
        this.remindAfterPeriod = remindAfterPeriod;
    }

    public Duration getRealization() {
        return realization;
    }

    public void setRealization(Duration realization) {
        this.realization = realization;
    }

    public List<CreateRecipeIngredientRequestDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<CreateRecipeIngredientRequestDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
