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
    private long id;

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
    private Integer timerDurationInMinutes;

    @OneToMany(targetEntity = IngredientAmount.class, mappedBy = "recipeStep", cascade = CascadeType.ALL)
    private Collection<IngredientAmount> ingredients = new ArrayList<>();

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Recipe recipe;

    public RecipeStep() {
    }

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

    public int getRealizationTimeInMinutes() {
        return realizationTimeInMinutes;
    }

    public void setRealizationTimeInMinutes(int realizationTimeInMinutes) {
        this.realizationTimeInMinutes = realizationTimeInMinutes;
    }

    public Collection<IngredientAmount> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<IngredientAmount> ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getTimerDurationInMinutes() {
        return timerDurationInMinutes;
    }

    public void setTimerDurationInMinutes(int timerDurationInMinutes) {
        this.timerDurationInMinutes = timerDurationInMinutes;
    }

    public void addIngredient(IngredientAmount ingredientAmount) {
        ingredients.add(ingredientAmount);
    }
}
