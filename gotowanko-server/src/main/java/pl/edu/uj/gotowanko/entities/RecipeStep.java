package pl.edu.uj.gotowanko.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by alanhawrot on 18.03.15.
 */
@Entity(name = "RecipeSteps")
public class RecipeStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long stepNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column
    private String videoUrl;

    @Column
    private String photoUrl;

    @Column
    private Integer realizationTimeInMinutes;

    @Column
    private Integer timerDurationInSeconds;

    @OneToMany(targetEntity = IngredientAmount.class, mappedBy = "recipeStep", cascade = CascadeType.ALL)
    private Collection<IngredientAmount> ingredients = new ArrayList<>();

    @ManyToOne(optional = false)
    private Recipe recipe;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getRealizationTimeInMinutes() {
        return realizationTimeInMinutes;
    }

    public void setRealizationTimeInMinutes(Integer realizationTimeInSeconds) {
        this.realizationTimeInMinutes = realizationTimeInSeconds;
    }

    public Collection<IngredientAmount> getIngredients() {
        return ingredients;
    }

    private void setIngredients(Collection<IngredientAmount> ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Integer getTimerDurationInSeconds() {
        return timerDurationInSeconds;
    }

    public void setTimerDurationInSeconds(Integer timerDurationInMinutes) {
        this.timerDurationInSeconds = timerDurationInMinutes;
    }

    public void addIngredient(IngredientAmount ingredientAmount) {
        ingredients.add(ingredientAmount);
    }

    public Long getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Long stepNumber) {
        this.stepNumber = stepNumber;
    }
}
