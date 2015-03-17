package pl.gotowanko.android.db.dao;

import java.util.List;

import pl.gotowanko.android.db.entity.Card;
import pl.gotowanko.android.db.entity.IngredientAmmount;

public interface IngredientAmmountDAO {

	List<IngredientAmmount> filter(Card card);

	void insert(IngredientAmmount ingredientAmmount);

	void update(IngredientAmmount ingredientAmmount);

	void delete(IngredientAmmount ingredientAmmount);

}
