package pl.edu.uj.gotowanko.controllers.recipes.dto;

import pl.edu.uj.gotowanko.entities.Comment;
import pl.edu.uj.gotowanko.entities.RecipeStep;
import pl.edu.uj.gotowanko.entities.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by alanhawrot on 19.04.15.
 */
public class LikedRecipeResponseDTO {

    private long id;
    private String title;
    private String photoUrl;
    private Integer cookingTimeInMinutes;
    private Integer approximateCost;
    private Integer numberOfLikes;
    private Calendar dateAdded;
    private Calendar lastEdited;
    private Collection<RecipeStep> recipeSteps = new ArrayList<>();
    private User user;
    private Collection<Comment> comments = new HashSet<>();
    private Collection<User> userLikes = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getCookingTimeInMinutes() {
        return cookingTimeInMinutes;
    }

    public void setCookingTimeInMinutes(Integer cookingTimeInMinutes) {
        this.cookingTimeInMinutes = cookingTimeInMinutes;
    }

    public Integer getApproximateCost() {
        return approximateCost;
    }

    public void setApproximateCost(Integer approximateCost) {
        this.approximateCost = approximateCost;
    }

    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Calendar getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Calendar dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Calendar getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Calendar lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Collection<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(Collection<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public Collection<User> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Collection<User> userLikes) {
        this.userLikes = userLikes;
    }
}
