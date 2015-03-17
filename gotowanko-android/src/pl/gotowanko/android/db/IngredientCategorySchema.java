package pl.gotowanko.android.db;

import android.provider.BaseColumns;

public class IngredientCategorySchema implements BaseColumns {
	public static final String TABLE_NAME = "ingredient_category";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String IMAGE = "image_path";
	public static final String AMMOUNT = "ammount";
	public static final String UNIT = "unit";

	public static String createTable() {
		String format = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY autoincrement, %s TEXT, %s TEXT, %s TEXT, %s TEXT)";
		return String.format(format, TABLE_NAME, ID, NAME, IMAGE, AMMOUNT, UNIT);
	}

	public static String dropTable() {
		String format = "DROP TABLE IF EXISTS %s";
		return String.format(format, TABLE_NAME);
	}

}