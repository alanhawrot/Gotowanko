package pl.gotowanko.android.db.dao;

import java.util.List;

import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.db.entity.IngredientCategory;

public interface IngredientDAO {
	void insert(Ingredient ingredient);

	void update(Ingredient ingredient);

	void delete(Ingredient ingredient);

	List<Ingredient> filter(String name);

	List<Ingredient> filter(IngredientCategory ingredientCategory);

	Ingredient findById(long id);
}
