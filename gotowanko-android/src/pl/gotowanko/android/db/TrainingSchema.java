package pl.gotowanko.android.db;

import android.provider.BaseColumns;

public class TrainingSchema implements BaseColumns {
	public static final String TABLE_NAME = "training";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String IMAGE = "image_path";
	public static final String MODIFICATION_DATE = "modification_date";

	public static String createTable() {
		String format = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY autoincrement, %s TEXT, %s TEXT, %s TEXT)";
		return String.format(format, TABLE_NAME, ID, NAME, IMAGE, MODIFICATION_DATE);
	}

	public static String dropTable() {
		String format = "DROP TABLE IF EXISTS %s";
		return String.format(format, TABLE_NAME);
	}

}