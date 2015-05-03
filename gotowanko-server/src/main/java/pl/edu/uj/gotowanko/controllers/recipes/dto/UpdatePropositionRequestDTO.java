package pl.edu.uj.gotowanko.controllers.recipes.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.Duration;
import java.util.Collection;

/**
 * Created by michal on 03.05.15.
 */
public class UpdatePropositionRequestDTO {
    @NotBlank
    private String title;

    @NotEmpty
    private Collection<UpdatePropositionRecipeStepRequestDTO> recipeSteps;

    private Integer approximateCost;
    private Duration cookingTime;
    private String photoUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Collection<UpdatePropositionRecipeStepRequestDTO> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(Collection<UpdatePropositionRecipeStepRequestDTO> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }
}
