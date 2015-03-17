package pl.gotowanko.android.db;

import android.provider.BaseColumns;

public class IngredientAmmountSchema implements BaseColumns {
	public static final String TABLE_NAME = "ingredient_ammount";
	public static final String ID = "id";
	public static final String INGREDIENT = "ingredient";
	public static final String AMMOUNT = "ammount";
	public static final String UNIT = "defaultunit";

	public static String createTable() {
		String format = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY autoincrement, %s TEXT, %s REAL, %s TEXT)";
		return String.format(format, TABLE_NAME, ID, INGREDIENT, AMMOUNT, UNIT);
	}

	public static String dropTable() {
		String format = "DROP TABLE IF EXISTS %s";
		return String.format(format, TABLE_NAME);
	}
}