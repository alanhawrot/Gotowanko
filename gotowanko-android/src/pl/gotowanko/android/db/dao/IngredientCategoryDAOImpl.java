package pl.gotowanko.android.db.dao;

import static pl.gotowanko.android.db.IngredientCategorySchema.ID;
import static pl.gotowanko.android.db.IngredientCategorySchema.IMAGE;
import static pl.gotowanko.android.db.IngredientCategorySchema.NAME;
import static pl.gotowanko.android.db.IngredientCategorySchema.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import pl.gotowanko.android.db.IngredientCategoryCategoriesSchema;
import pl.gotowanko.android.db.IngredientCategoryIngredientsSchema;
import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.db.entity.IngredientCategory;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class IngredientCategoryDAOImpl extends BaseDAO implements IngredientCategoryDAO {
	private IngredientDAO ingredientDAO;

	public IngredientCategoryDAOImpl(SQLiteDatabase db, IngredientDAO ingredientDAO) {
		super(db);
		this.ingredientDAO = ingredientDAO;
	}

	@Override
	public void insert(IngredientCategory ingredientCategory) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(NAME, ingredientCategory.getName());
		contentValues.put(IMAGE, ingredientCategory.getImage());
		ingredientCategory.setId(db.insert(TABLE_NAME, null, contentValues));

		for (Ingredient i : ingredientCategory.getIngredients()) {
			ingredientDAO.insert(i);
			setRelation(IngredientCategoryIngredientsSchema.TABLE_NAME, IngredientCategoryIngredientsSchema.CATEGORY_ID, ingredientCategory.getId(),
					IngredientCategoryIngredientsSchema.INGREDIENT_ID, i.getId());
		}

		for (IngredientCategory c : ingredientCategory.getCategories()) {
			insert(c);
			setRelation(IngredientCategoryCategoriesSchema.TABLE_NAME, IngredientCategoryCategoriesSchema.CATEGORY_ID, ingredientCategory.getId(),
					IngredientCategoryCategoriesSchema.CHILD_CATEGORY_ID, c.getId());
		}

	}

	@Override
	public void update(IngredientCategory ingredientCategory) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(NAME, ingredientCategory.getName());
		contentValues.put(IMAGE, ingredientCategory.getImage());
		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(ingredientCategory.getId()) };
		db.update(TABLE_NAME, contentValues, selection, selectionArgs);

		removeRelations(IngredientCategoryIngredientsSchema.TABLE_NAME, IngredientCategoryIngredientsSchema.CATEGORY_ID, ingredientCategory.getId());
		removeRelations(IngredientCategoryCategoriesSchema.TABLE_NAME, IngredientCategoryCategoriesSchema.CATEGORY_ID, ingredientCategory.getId());
		for (Ingredient i : ingredientCategory.getIngredients()) {
			ingredientDAO.update(i);
			setRelation(IngredientCategoryIngredientsSchema.TABLE_NAME, IngredientCategoryIngredientsSchema.CATEGORY_ID, ingredientCategory.getId(),
					IngredientCategoryIngredientsSchema.INGREDIENT_ID, i.getId());
		}

		for (IngredientCategory c : ingredientCategory.getCategories()) {
			update(c);
			setRelation(IngredientCategoryCategoriesSchema.TABLE_NAME, IngredientCategoryCategoriesSchema.CATEGORY_ID, ingredientCategory.getId(),
					IngredientCategoryCategoriesSchema.CHILD_CATEGORY_ID, c.getId());
		}
	}

	@Override
	public void delete(IngredientCategory ingredientCategory) {

		removeRelations(IngredientCategoryIngredientsSchema.TABLE_NAME, IngredientCategoryIngredientsSchema.CATEGORY_ID, ingredientCategory.getId());
		removeRelations(IngredientCategoryCategoriesSchema.TABLE_NAME, IngredientCategoryCategoriesSchema.CATEGORY_ID, ingredientCategory.getId());

		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(ingredientCategory.getId()) };
		db.delete(TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public IngredientCategory getRoot() {
		String[] selectionArgs = { "1" };
		StringBuilder b = new StringBuilder("");
		b.append(String.format("SELECT %s, %s, %s FROM %s WHERE id = ?", ID, NAME, IMAGE, TABLE_NAME));
		final Cursor cursor = db.rawQuery(b.toString(), selectionArgs);
		cursor.moveToFirst();
		IngredientCategory category = new IngredientCategory(this, ingredientDAO);
		category.setId(cursor.getLong(0));
		category.setName(cursor.getString(1));
		category.setImage(cursor.getString(2));
		return category;
	}

	@Override
	public List<IngredientCategory> filter(IngredientCategory ingredientCategory) {
		List<IngredientCategory> l = new ArrayList<IngredientCategory>();
		String[] selectionArgs = { String.valueOf(ingredientCategory.getId()) };
		StringBuilder b = new StringBuilder("SELECT ");
		b.append(String.format("ic.%s, ic.%s, ic.%s ", ID, NAME, IMAGE));
		b.append(String.format("FROM %s ic ", TABLE_NAME));
		b.append(String.format("INNER JOIN %s icc ", IngredientCategoryCategoriesSchema.TABLE_NAME));
		b.append(String.format("ON ic.%s = icc.%s ", ID, IngredientCategoryCategoriesSchema.CHILD_CATEGORY_ID));
		b.append(String.format("WHERE icc.%s = ?", IngredientCategoryCategoriesSchema.CATEGORY_ID));
		final Cursor cursor = db.rawQuery(b.toString(), selectionArgs);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				IngredientCategory category = new IngredientCategory(this, ingredientDAO);
				category.setId(cursor.getLong(0));
				category.setName(cursor.getString(1));
				category.setImage(cursor.getString(2));
				l.add(category);
			} while (cursor.moveToNext());
		}
		return l;
	}

	@Override
	public IngredientCategory newIngredientCategory() {
		return new IngredientCategory(this, ingredientDAO);
	}

}
