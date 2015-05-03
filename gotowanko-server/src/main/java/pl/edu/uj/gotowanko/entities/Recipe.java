package pl.edu.uj.gotowanko.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by alanhawrot on 18.03.15.
 */
@Entity(name = "Recipes")
public class Recipe {

    private static final String DEFAULT_RECIPE_IMAGE = "/images/recipe/noImage.png";

    public enum RecipeState {
        NORMAL, UPDATE_PROPOSITION;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecipeState state;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @ColumnDefault(value = "'" + DEFAULT_RECIPE_IMAGE + "'")
    private String photoUrl;

    @Column
    private Integer cookingTimeInMinutes;

    @Column
    private Integer approximateCost;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Calendar dateAdded;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar lastEdited;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Collection<RecipeStep> recipeSteps = new ArrayList<>();

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Collection<Comment> comments = new HashSet<>();

    @ManyToMany(targetEntity = User.class, mappedBy = "recipeLikes")
    private Collection<User> userLikes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        this.photoUrl = photoUrl == null ? Recipe.DEFAULT_RECIPE_IMAGE : photoUrl;
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

    private void setRecipeSteps(Collection<RecipeStep> recipeSteps) {
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

    private void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public void addRecipeStep(RecipeStep recipeStep) {
        recipeSteps.add(recipeStep);
    }

    public Collection<User> getUserLikes() {
        return userLikes;
    }

    private void setUserLikes(Collection<User> userLikes) {
        this.userLikes = userLikes;
    }

    public void addUserLike(User user) {
        getUserLikes().add(user);
    }

    public void removeUserLike(User user) {
        getUserLikes().remove(user);
    }

    public boolean containsUserLike(User user) {
        return getUserLikes().contains(user);
    }

    public RecipeState getState() {
        return state;
    }

    public void setState(RecipeState state) {
        this.state = state;
    }
}
