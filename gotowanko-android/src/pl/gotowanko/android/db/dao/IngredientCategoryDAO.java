package pl.gotowanko.android.db.dao;

import java.util.List;

import pl.gotowanko.android.db.entity.IngredientCategory;

public interface IngredientCategoryDAO {
	void insert(IngredientCategory ingredientCategory);

	void update(IngredientCategory ingredientCategory);

	void delete(IngredientCategory ingredientCategory);

	IngredientCategory getRoot();

	List<IngredientCategory> filter(IngredientCategory ingredientCategory);

	IngredientCategory newIngredientCategory();
}
