package pl.gotowanko.android.db.dao;

import static pl.gotowanko.android.db.IngredientSchema.DEFAULT_UNIT;
import static pl.gotowanko.android.db.IngredientSchema.ID;
import static pl.gotowanko.android.db.IngredientSchema.IMAGE;
import static pl.gotowanko.android.db.IngredientSchema.NAME;
import static pl.gotowanko.android.db.IngredientSchema.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import pl.gotowanko.android.db.IngredientCategoryIngredientsSchema;
import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.db.entity.IngredientCategory;
import pl.gotowanko.android.db.entity.Unit;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class IngredientDAOImpl implements IngredientDAO {
	private SQLiteDatabase db;

	public IngredientDAOImpl(SQLiteDatabase db) {
		super();
		this.db = db;
	}

	@Override
	public void insert(Ingredient ingredient) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(NAME, ingredient.getName());
		contentValues.put(IMAGE, ingredient.getImage());
		contentValues.put(DEFAULT_UNIT, ingredient.getDefaultUnit().toString());
		ingredient.setId(db.insert(TABLE_NAME, null, contentValues));

	}

	@Override
	public void update(Ingredient ingredient) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(NAME, ingredient.getName());
		contentValues.put(IMAGE, ingredient.getImage());
		contentValues.put(DEFAULT_UNIT, ingredient.getDefaultUnit().toString());
		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(ingredient.getId()) };
		db.update(TABLE_NAME, contentValues, selection, selectionArgs);
	}

	@Override
	public void delete(Ingredient ingredient) {
		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(ingredient.getId()) };
		db.delete(TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public List<Ingredient> filter(String name) {
		List<Ingredient> l = new ArrayList<Ingredient>();
		String selection = String.format("UPPER(%s) LIKE %%UPPER(%s)%%", NAME, name);
		String[] selectionArgs = { name };
		String[] collumns = new String[] { ID, NAME, IMAGE, };
		Cursor cursor = db.query(TABLE_NAME, collumns, selection, selectionArgs, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Ingredient i = new Ingredient();
				i.setId(cursor.getLong(0));
				i.setName(cursor.getString(1));
				i.setImage(cursor.getString(2));
				l.add(i);
			} while (cursor.moveToNext());
		}
		return l;

	}

	@Override
	public List<Ingredient> filter(IngredientCategory ingredientCategory) {
		List<Ingredient> l = new ArrayList<Ingredient>();
		StringBuilder b = new StringBuilder("SELECT ");
		b.append(String.format("%s, %s, %s, %s ", ID, NAME, IMAGE, DEFAULT_UNIT));
		b.append(String.format("FROM %s i ", TABLE_NAME));
		b.append(String.format("INNER JOIN %s ici ON ici.%s = i.%s ", IngredientCategoryIngredientsSchema.TABLE_NAME, IngredientCategoryIngredientsSchema.INGREDIENT_ID, ID));
		b.append(String.format("WHERE ici.%s = ?", IngredientCategoryIngredientsSchema.CATEGORY_ID));
		String[] selectionArgs = { String.valueOf(ingredientCategory.getId()) };
		Cursor cursor = db.rawQuery(b.toString(), selectionArgs);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Ingredient i = new Ingredient();
				i.setId(cursor.getLong(0));
				i.setName(cursor.getString(1));
				i.setImage(cursor.getString(2));
				i.setDefaultUnit(Unit.fromString(cursor.getString(3)));
				l.add(i);
			} while (cursor.moveToNext());
		}
		return l;

	}

	@Override
	public Ingredient findById(long id) {
		StringBuilder b = new StringBuilder("SELECT ");
		b.append(String.format("%s, %s, %s, %s ", ID, NAME, IMAGE, DEFAULT_UNIT));
		b.append(String.format("FROM %s i ", TABLE_NAME));
		b.append(String.format("WHERE i.%s = ?", ID));
		String[] selectionArgs = { String.valueOf(id) };
		Cursor cursor = db.rawQuery(b.toString(), selectionArgs);
		if (cursor != null && cursor.moveToFirst()) {

			Ingredient i = new Ingredient();
			i.setId(cursor.getLong(0));
			i.setName(cursor.getString(1));
			i.setImage(cursor.getString(2));
			i.setDefaultUnit(Unit.fromString(cursor.getString(3)));
			return i;
		}
		return null;

	}

}
