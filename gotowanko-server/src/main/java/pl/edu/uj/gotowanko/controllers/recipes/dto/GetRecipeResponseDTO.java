package pl.edu.uj.gotowanko.controllers.recipes.dto;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by michal on 03.05.15.
 */
public class GetRecipeResponseDTO {
    private String title;
    private Calendar dateAdded;
    private Calendar lastEdited;
    private Integer approximateCost;
    private Integer likesNumber;
    private Duration cookingTime;
    private String photoUrl;
    private Collection<GetRecipeStepsResponseDTO> recipeSteps = new ArrayList<>();
    private Collection<GetRecipeCommentResponseDTO> comments = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateAdded(Calendar dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setLastEdited(Calendar lastEdited) {
        this.lastEdited = lastEdited;
    }

    public void setApproximateCost(Integer approximateCost) {
        this.approximateCost = approximateCost;
    }

    public void setLikesNumber(Integer likesNumber) {
        this.likesNumber = likesNumber;
    }

    public void setCookingTime(Duration cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setRecipeSteps(Collection<GetRecipeStepsResponseDTO> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public void setComments(Collection<GetRecipeCommentResponseDTO> comments) {
        this.comments = comments;
    }

    public Collection<GetRecipeStepsResponseDTO> getRecipeSteps() {
        return recipeSteps;
    }

    public Collection<GetRecipeCommentResponseDTO> getComments() {
        return comments;
    }

    public String getTitle() {
        return title;
    }

    public Calendar getDateAdded() {
        return dateAdded;
    }

    public Calendar getLastEdited() {
        return lastEdited;
    }

    public Integer getApproximateCost() {
        return approximateCost;
    }

    public Integer getLikesNumber() {
        return likesNumber;
    }

    public Duration getCookingTime() {
        return cookingTime;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }


}
