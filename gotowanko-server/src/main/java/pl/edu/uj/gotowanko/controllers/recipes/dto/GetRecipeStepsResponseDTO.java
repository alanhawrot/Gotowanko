package pl.edu.uj.gotowanko.controllers.recipes.dto;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by michal on 03.05.15.
 */
public class GetRecipeStepsResponseDTO {
    private String title;
    private String description;
    private Collection<GetRecipeStepIngredientResponseDTO> ingredients = new ArrayList<>();
    private String photoUrl;
    private String videoUrl;
    private Duration realizationTime;
    private Duration timerDuration;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(Collection<GetRecipeStepIngredientResponseDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setRealizationTime(Duration realizationTime) {
        this.realizationTime = realizationTime;
    }

    public void setTimerDuration(Duration timerDuration) {
        this.timerDuration = timerDuration;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Collection<GetRecipeStepIngredientResponseDTO> getIngredients() {
        return ingredients;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Duration getRealizationTime() {
        return realizationTime;
    }

    public Duration getTimerDuration() {
        return timerDuration;
    }


}
