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

    @NotEmpty
    private List<CreateRecipeStepRequestDTO> recipeSteps;

    private Integer approximateCost;

    private Duration cookingTime;

    private String photoUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Duration getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Duration cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
