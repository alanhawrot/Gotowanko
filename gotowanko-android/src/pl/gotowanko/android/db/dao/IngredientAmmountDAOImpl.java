package pl.gotowanko.android.db.dao;

import static pl.gotowanko.android.db.IngredientAmmountSchema.AMMOUNT;
import static pl.gotowanko.android.db.IngredientAmmountSchema.ID;
import static pl.gotowanko.android.db.IngredientAmmountSchema.INGREDIENT;
import static pl.gotowanko.android.db.IngredientAmmountSchema.TABLE_NAME;
import static pl.gotowanko.android.db.IngredientAmmountSchema.UNIT;

import java.util.ArrayList;
import java.util.List;

import pl.gotowanko.android.db.CardIngredientsSchema;
import pl.gotowanko.android.db.entity.Card;
import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.db.entity.Unit;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class IngredientAmmountDAOImpl implements IngredientAmmountDAO {

	private SQLiteDatabase db;
	private IngredientDAO ingredientDAO;

	public IngredientAmmountDAOImpl(SQLiteDatabase db, IngredientDAO ingredientDAO) {
		this.ingredientDAO = ingredientDAO;
		this.db = db;
	}

	@Override
	public List<IngredientAmmount> filter(Card card) {
		List<IngredientAmmount> l = new ArrayList<IngredientAmmount>();
		StringBuilder b = new StringBuilder("SELECT id, ammount, defaultunit, ingredient ");
		b.append(String.format("FROM %s ia ", TABLE_NAME));
		b.append(String.format("INNER JOIN %s c ON c.%s = ia.%s ", CardIngredientsSchema.TABLE_NAME, CardIngredientsSchema.INGREDIENT_ID, ID));
		b.append(String.format("WHERE c.%s = ?", CardIngredientsSchema.CARD_ID));
		String[] selectionArgs = { String.valueOf(card.getId()) };
		Cursor cursor = db.rawQuery(b.toString(), selectionArgs);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				IngredientAmmount i = new IngredientAmmount();
				i.setId(cursor.getLong(cursor.getColumnIndex(ID)));
				i.setAmmount(cursor.getDouble(cursor.getColumnIndex(AMMOUNT)));
				i.setUnit(Unit.fromString(cursor.getString(cursor.getColumnIndex(UNIT))));
				long ingredientId = cursor.getLong(cursor.getColumnIndex(INGREDIENT));
				Ingredient ingredient = ingredientDAO.findById(ingredientId);
				i.setIngredient(ingredient);
				l.add(i);
			} while (cursor.moveToNext());
		}
		return l;
	}

	@Override
	public void insert(IngredientAmmount ingredient) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(AMMOUNT, ingredient.getAmmount());
		contentValues.put(INGREDIENT, ingredient.getIngredient().getId());
		contentValues.put(UNIT, ingredient.getUnit().toString());
		ingredient.setId(db.insert(TABLE_NAME, null, contentValues));

	}

	@Override
	public void update(IngredientAmmount ingredient) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(AMMOUNT, ingredient.getAmmount());
		contentValues.put(INGREDIENT, ingredient.getIngredient().getId());
		contentValues.put(UNIT, ingredient.getUnit().toString());
		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(ingredient.getId()) };
		db.update(TABLE_NAME, contentValues, selection, selectionArgs);
	}

	@Override
	public void delete(IngredientAmmount ingredient) {
		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(ingredient.getId()) };
		db.delete(TABLE_NAME, selection, selectionArgs);
	}
}
