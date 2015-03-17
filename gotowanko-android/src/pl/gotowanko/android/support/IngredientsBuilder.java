package pl.gotowanko.android.support;

import java.util.List;

import pl.gotowanko.android.R;
import pl.gotowanko.android.db.dao.IngredientCategoryDAO;
import pl.gotowanko.android.db.dao.IngredientDAO;
import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.db.entity.IngredientCategory;
import pl.gotowanko.android.db.entity.Unit;
import android.content.Context;
import android.content.res.Resources;

public class IngredientsBuilder {
	private IngredientCategoryDAO icDAO;
	private Resources res;

	public IngredientsBuilder(Context context, IngredientCategoryDAO icDAO, IngredientDAO iDAO) {
		this.icDAO = icDAO;
		res = context.getResources();
	}

	public void build() {
		IngredientCategory root = icDAO.newIngredientCategory();
		root.setName("root");
		root.setImage(String.valueOf(R.drawable.noimage));
		List<Ingredient> rootIngredients = root.getIngredients();

		IngredientCategory meat = icDAO.newIngredientCategory();
		meat.setName(res.getString(R.string.meat));
		meat.setImage(String.valueOf(R.drawable.category));
		meat.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.steak), R.drawable.steak2));
		meat.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.fish), R.drawable.fish52));
		meat.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.chicken), R.drawable.chicken));
		meat.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.sausage), R.drawable.sausage));
		meat.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.turkey), R.drawable.turkey));
		meat.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.ham), R.drawable.ham));
		root.getCategories().add(meat);

		IngredientCategory fruits = icDAO.newIngredientCategory();
		fruits.setName(res.getString(R.string.fruits));
		fruits.setImage(String.valueOf(R.drawable.category));
		fruits.getIngredients().add(createIngredient(Unit.SZT, res.getString(R.string.apples), R.drawable.apple54));
		fruits.getIngredients().add(createIngredient(Unit.DAG, res.getString(R.string.cherries), R.drawable.cherries));
		fruits.getIngredients().add(createIngredient(Unit.DAG, res.getString(R.string.grapes), R.drawable.grapes));
		fruits.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.watermelon), R.drawable.watermelon));
		fruits.getIngredients().add(createIngredient(Unit.DAG, res.getString(R.string.bananas), R.drawable.bananas));
		fruits.getIngredients().add(createIngredient(Unit.DAG, res.getString(R.string.strawberry), R.drawable.strawberry7));
		root.getCategories().add(fruits);

		IngredientCategory vegetables = icDAO.newIngredientCategory();
		vegetables.setName(res.getString(R.string.vegetables));
		vegetables.setImage(String.valueOf(R.drawable.category));
		vegetables.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.pumpkin), R.drawable.pumpkin));
		vegetables.getIngredients().add(createIngredient(Unit.G, res.getString(R.string.chilis), R.drawable.chilis));
		vegetables.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.carrot), R.drawable.carrot3));
		vegetables.getIngredients().add(createIngredient(Unit.DAG, res.getString(R.string.onion), R.drawable.onion));
		root.getCategories().add(vegetables);

		IngredientCategory drinks = icDAO.newIngredientCategory();
		drinks.setName(res.getString(R.string.drinks));
		drinks.setImage(String.valueOf(R.drawable.category));
		drinks.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.tea), R.drawable.tea));
		drinks.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.beer), R.drawable.beer));
		drinks.getIngredients().add(createIngredient(Unit.KG, res.getString(R.string.cafe), R.drawable.cafe));
		root.getCategories().add(drinks);
		rootIngredients.add(createIngredient(Unit.KG, res.getString(R.string.rice), R.drawable.rice));
		rootIngredients.add(createIngredient(Unit.KG, res.getString(R.string.cheese), R.drawable.cheese));
		rootIngredients.add(createIngredient(Unit.DAG, res.getString(R.string.butter), R.drawable.butter));
		rootIngredients.add(createIngredient(Unit.SZT, res.getString(R.string.eggs), R.drawable.eggs));
		rootIngredients.add(createIngredient(Unit.KG, res.getString(R.string.bread), R.drawable.spongecake));

		icDAO.insert(root);
	}

	protected Ingredient createIngredient(Unit kg, String name, int img) {
		Ingredient d;
		d = new Ingredient();
		d.setDefaultUnit(kg);
		d.setImage(String.valueOf(img));
		d.setName(name);
		return d;
	}

}
