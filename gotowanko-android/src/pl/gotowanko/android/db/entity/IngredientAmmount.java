package pl.gotowanko.android.db.entity;

import java.io.Serializable;

public class IngredientAmmount implements Serializable {
	private static final long serialVersionUID = 6644760043397151588L;
	private Long id;
	private Ingredient ingredient;
	private Double ammount;
	private Unit unit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Double getAmmount() {
		return ammount;
	}

	public void setAmmount(Double ammount) {
		this.ammount = ammount;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
