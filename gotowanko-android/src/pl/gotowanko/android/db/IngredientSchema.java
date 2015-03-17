package pl.gotowanko.android.db;

import android.provider.BaseColumns;

public class IngredientSchema implements BaseColumns {
	public static final String TABLE_NAME = "ingredient";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String IMAGE = "image_path";
	public static final String DEFAULT_UNIT = "defaultunit";

	public static String createTable() {
		String format = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY autoincrement, %s TEXT, %s TEXT,  %s TEXT)";
		return String.format(format, TABLE_NAME, ID, NAME, IMAGE, DEFAULT_UNIT);
	}

	public static String dropTable() {
		String format = "DROP TABLE IF EXISTS %s";
		return String.format(format, TABLE_NAME);
	}
}