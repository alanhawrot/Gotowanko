package pl.edu.uj.gotowanko.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by michal on 18.03.15.
 */
@Entity(name = "IngredientCategories")
public class IngredientCategory extends Ingredient {

    @OneToMany
    private Collection<Ingredient> ingredients = new HashSet<>();

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    private void setIngredients(Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
