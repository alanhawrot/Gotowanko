package pl.gotowanko.android.db;

import android.provider.BaseColumns;

public class TrainingCardsSchema implements BaseColumns {
	public static final String TABLE_NAME = "training_cards";
	public static final String TRAINING_ID = "training_id";
	public static final String CARD_ID = "card_id";

	public static String createTable() {
		StringBuilder builder = new StringBuilder(String.format("CREATE TABLE %s(", TABLE_NAME));
		builder.append(String.format("%s INTEGER,", TRAINING_ID));
		builder.append(String.format("%s INTEGER", CARD_ID));
		// builder.append(String.format("FOREIGN KEY %s REFERENCES %s(%s),",
		// TRAINING_ID, TrainingSchema.TABLE_NAME, TrainingSchema.ID));
		// builder.append(String.format("FOREIGN KEY %s REFERENCES %s(%s)",
		// CARD_ID, CardSchema.TABLE_NAME, CardSchema.ID));
		builder.append(")");
		return builder.toString();
	}

	public static String dropTable() {
		String format = "DROP TABLE IF EXISTS %s";
		return String.format(format, TABLE_NAME);
	}
}