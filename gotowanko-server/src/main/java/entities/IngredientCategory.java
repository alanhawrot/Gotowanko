package entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by michal on 18.03.15.
 */

@Entity(name = "IngredientCategories")
public class IngredientCategory extends Ingredient {

    @OneToMany
    private Collection<Ingredient> ingredients;

    public IngredientCategory() {
    }

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
