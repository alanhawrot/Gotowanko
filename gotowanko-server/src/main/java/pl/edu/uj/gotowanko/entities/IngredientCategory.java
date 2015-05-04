package pl.edu.uj.gotowanko.entities;

import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by michal on 18.03.15.
 */
@Entity(name = "IngredientCategories")
public class IngredientCategory extends Ingredient {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "ingredientcategory_subingredients",
            joinColumns = @JoinColumn(name = "ingredientcategory"),
            inverseJoinColumns = @JoinColumn(name = "subingredient"))
    private Collection<Ingredient> ingredients = new HashSet<>();

    public IngredientCategory() {

    }

    public IngredientCategory(String name) {
        super(name);
    }

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
