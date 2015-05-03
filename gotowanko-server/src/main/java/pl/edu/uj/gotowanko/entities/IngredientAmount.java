package pl.edu.uj.gotowanko.entities;

import javax.persistence.*;

/**
 * Created by alanhawrot on 18.03.15.
 */
@Entity(name = "IngredientAmounts")
public class IngredientAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private RecipeStep recipeStep;

    @ManyToOne(optional = false)
    private Ingredient ingredient;

    @ManyToOne(optional = false)
    private IngredientUnit ingredientUnit;

    @Column(nullable = false)
    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
