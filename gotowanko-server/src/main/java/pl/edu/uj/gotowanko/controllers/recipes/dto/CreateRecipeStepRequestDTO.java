package pl.edu.uj.gotowanko.controllers.recipes.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;

/**
 * Created by michal on 17.04.15.
 */
public class CreateRecipeStepRequestDTO {

    @NotNull
    private Long stepNumber;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotEmpty
    private List<CreateRecipeIngredientRequestDTO> ingredients;

    private Duration timerDuration;

    private Duration realizationTime;

    private String photoUrl;

    private String videoUrl;

    public Long getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Long stepNumber) {
        this.stepNumber = stepNumber;
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

    public Duration getTimerDuration() {
        return timerDuration;
    }

    public void setTimerDuration(Duration timerDuration) {
        this.timerDuration = timerDuration;
    }

    public Duration getRealizationTime() {
        return realizationTime;
    }

    public void setRealizationTime(Duration realizationTime) {
        this.realizationTime = realizationTime;
    }

    public List<CreateRecipeIngredientRequestDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<CreateRecipeIngredientRequestDTO> ingredients) {
        this.ingredients = ingredients;
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
}
