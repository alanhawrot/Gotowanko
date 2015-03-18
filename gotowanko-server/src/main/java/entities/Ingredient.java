package entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by alanhawrot on 18.03.15.
 */
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany(targetEntity = Recipe.class)
    private Collection<Recipe> recipes;

    @ManyToMany(targetEntity = RecipeStep.class)
    private Collection<RecipeStep> recipeSteps;

    public Ingredient() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Collection<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(Collection<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }
}
