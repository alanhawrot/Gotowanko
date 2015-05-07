package pl.edu.uj.gotowanko.controllers.recipes.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import pl.edu.uj.gotowanko.controllers.recipes.dto.UpdateRecipeStepRequestDTO;

import java.time.Duration;
import java.util.Collection;

/**
 * Created by michal on 03.05.15.
 */
public class UpdateRecipeRequestDTO {
    @NotBlank
    private String title;

    @NotEmpty
    private Collection<UpdateRecipeStepRequestDTO> recipeSteps;

    private Integer approximateCost;
    private String photoUrl;
    private Duration cookingTime;

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Collection<UpdateRecipeStepRequestDTO> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(Collection<UpdateRecipeStepRequestDTO> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public Duration getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Duration cookingTime) {
        this.cookingTime = cookingTime;
    }
}
