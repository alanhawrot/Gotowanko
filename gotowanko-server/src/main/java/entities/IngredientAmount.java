package entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by alanhawrot on 18.03.15.
 */
@Entity(name = "IngredientAmounts")
public class IngredientAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    private RecipeStep recipeStep;

    @ManyToOne(optional = false)
    private Ingredient ingredient;

    @ManyToOne(optional = false)
    private IngredientUnit ingredientUnit;

    @Column(nullable = false)
    private Long amount;

    public IngredientAmount() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(IngredientUnit ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public RecipeStep getRecipeStep() {
        return recipeStep;
    }

    public void setRecipeStep(RecipeStep recipeStep) {
        this.recipeStep = recipeStep;
    }
}
