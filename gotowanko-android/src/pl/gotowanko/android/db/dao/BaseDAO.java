package pl.gotowanko.android.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class BaseDAO {
	protected SQLiteDatabase db;

	public BaseDAO(SQLiteDatabase db) {
		super();
		this.db = db;
	}

	protected Long setRelation(String tableName, String firstColumn, Long firstId, String secondColumn, Long secondId) {
		removeRelation(tableName, firstColumn, firstId, secondColumn, secondId);
		return putRelation(tableName, firstColumn, firstId, secondColumn, secondId);
	}

	protected Long putRelation(String tableName, String firstColumn, Long firstId, String secondColumn, Long secondId) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(firstColumn, firstId);
		contentValues.put(secondColumn, secondId);
		return db.insert(tableName, null, contentValues);
	}

	protected int removeRelation(String tableName, String firstColumn, Long firstId, String secondColumn, Long secondId) {
		String selection = String.format("%s = ? and %s = ?", secondColumn, firstColumn);
		return db.delete(tableName, selection, new String[] { String.valueOf(secondId), String.valueOf(firstId) });
	}

	protected int removeRelations(String tableName, String column, Long id) {
		String selection = String.format("%s = ? ", column, id);
		return db.delete(tableName, selection, new String[] { String.valueOf(id) });
	}

}