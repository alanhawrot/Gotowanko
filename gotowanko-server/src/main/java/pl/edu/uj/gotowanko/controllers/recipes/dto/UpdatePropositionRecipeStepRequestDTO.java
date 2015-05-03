package pl.edu.uj.gotowanko.controllers.recipes.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Collection;

/**
 * Created by michal on 03.05.15.
 */
public class UpdatePropositionRecipeStepRequestDTO {

    @Min(0)
    @NotNull
    private Long stepNumber;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotEmpty
    private Collection<UpdateRecipePropositionIngredientRequestDTO> ingredients;

    private String photoUrl;
    private String videoUrl;
    private Duration realizationTime;
    private Duration timerDuration;

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Duration getRealizationTime() {
        return realizationTime;
    }

    public void setRealizationTime(Duration realizationTime) {
        this.realizationTime = realizationTime;
    }

    public Duration getTimerDuration() {
        return timerDuration;
    }

    public void setTimerDuration(Duration timerDuration) {
        this.timerDuration = timerDuration;
    }

    public Collection<UpdateRecipePropositionIngredientRequestDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<UpdateRecipePropositionIngredientRequestDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public Long getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Long stepNumber) {
        this.stepNumber = stepNumber;
    }
}
