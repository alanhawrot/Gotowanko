package pl.gotowanko.android.db;

import android.provider.BaseColumns;

public class IngredientAmmountIngredientSchema implements BaseColumns {
	public static final String TABLE_NAME = "ingredient_ammount_ingredient";
	public static final String INGREDIENT_AMMOUNT_ID = "ingredient_ammount_id";
	public static final String INGREDIENT_ID = "ingredient_id";

	public static String createTable() {
		StringBuilder builder = new StringBuilder(String.format("CREATE TABLE %s(", TABLE_NAME));
		builder.append(String.format("%s INTEGER,", INGREDIENT_ID));
		builder.append(String.format("%s INTEGER", INGREDIENT_AMMOUNT_ID));
		// builder.append(String.format("FOREIGN KEY %s REFERENCES %s(%s),",
		// INGREDIENT_ID, IngredientCategorySchema.TABLE_NAME,
		// IngredientCategorySchema.ID));
		// builder.append(String.format("FOREIGN KEY %s REFERENCES %s(%s)",
		// INGREDIENT_AMMOUNT_ID, IngredientCategorySchema.TABLE_NAME,
		// IngredientCategorySchema.ID));
		builder.append(")");
		return builder.toString();
	}

	public static String dropTable() {
		String format = "DROP TABLE IF EXISTS %s";
		return String.format(format, TABLE_NAME);
	}

}