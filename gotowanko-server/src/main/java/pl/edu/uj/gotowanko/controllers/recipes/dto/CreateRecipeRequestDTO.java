package pl.edu.uj.gotowanko.controllers.recipes.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.Duration;
import java.util.List;

/**
 * Created by michal on 17.04.15.
 */
public class CreateRecipeRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotEmpty
    private List<CreateRecipeStepRequestDTO> recipeSteps;

    private Integer approximateCost;

    private Duration cookingTimeInMinutes;

    private String photoUrl;


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

    public List<CreateRecipeStepRequestDTO> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(List<CreateRecipeStepRequestDTO> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public Integer getApproximateCost() {
        return approximateCost;
    }

    public void setApproximateCost(Integer approximateCost) {
        this.approximateCost = approximateCost;
    }

    public Duration getCookingTimeInMinutes() {
        return cookingTimeInMinutes;
    }

    public void setCookingTimeInMinutes(Duration cookingTimeInMinutes) {
        this.cookingTimeInMinutes = cookingTimeInMinutes;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
