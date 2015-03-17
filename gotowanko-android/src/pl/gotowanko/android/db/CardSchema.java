package pl.gotowanko.android.db;

import android.provider.BaseColumns;

public class CardSchema implements BaseColumns {
	public static final String TABLE_NAME = "card";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String IMAGE = "image_path";
	public static final String DURATION = "expected_time_duration";
	public static final String TIMER_DURATION = "timer_duration";

	public static String createTable() {
		String format = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY autoincrement, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)";
		return String.format(format, TABLE_NAME, ID, TITLE, DESCRIPTION, IMAGE, DURATION, TIMER_DURATION);
	}

	public static String dropTable() {
		String format = "DROP TABLE IF EXISTS %s";
		return String.format(format, TABLE_NAME);
	}

}