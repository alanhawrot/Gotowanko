package pl.edu.uj.gotowanko.entities;

import javax.persistence.*;

/**
 * Created by michal on 03.05.15.
 */
@Entity
public class RecipeUpdateProposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    private Recipe currentRecipe;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    private Recipe updatedRecipe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }

    public Recipe getUpdatedRecipe() {
        return updatedRecipe;
    }

    public void setUpdatedRecipe(Recipe updatedRecipe) {
        this.updatedRecipe = updatedRecipe;
    }
}
