package entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by michal on 18.03.15.
 */

@Entity
public class IngredientCategory extends Ingredient {

    @OneToMany
    Collection<Ingredient> ingredients;

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
