package pl.gotowanko.android.db;

import android.provider.BaseColumns;

public class IngredientCategoryCategoriesSchema implements BaseColumns {
	public static final String TABLE_NAME = "ingredient_category_categories";
	public static final String CATEGORY_ID = "category_id";
	public static final String CHILD_CATEGORY_ID = "child_category_id";

	public static String createTable() {
		StringBuilder builder = new StringBuilder(String.format("CREATE TABLE %s(", TABLE_NAME));
		builder.append(String.format("%s INTEGER, ", CATEGORY_ID));
		builder.append(String.format("%s INTEGER ", CHILD_CATEGORY_ID));
		// builder.append(String.format("FOREIGN KEY %s REFERENCES %s(%s), ",
		// CATEGORY_ID, IngredientCategorySchema.TABLE_NAME,
		// IngredientCategorySchema.ID));
		// builder.append(String.format("FOREIGN KEY %s REFERENCES %s(%s)",
		// CHILD_CATEGORY_ID, IngredientCategorySchema.TABLE_NAME,
		// IngredientCategorySchema.ID));
		builder.append(")");
		return builder.toString();
	}

	public static String dropTable() {
		String format = "DROP TABLE IF EXISTS %s";
		return String.format(format, TABLE_NAME);
	}

}