package pl.edu.uj.gotowanko.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by michal on 18.03.15.
 */
@Entity(name = "IngredientCategories")
public class IngredientCategory extends Ingredient {

    @ManyToMany
    @JoinTable(name = "ingredientcategory_subingredients",
            joinColumns = @JoinColumn(name = "ingredientcategory"),
            inverseJoinColumns = @JoinColumn(name = "subingredient"))
    private Collection<Ingredient> ingredients = new HashSet<>();

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    private void setIngredients(Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
}
