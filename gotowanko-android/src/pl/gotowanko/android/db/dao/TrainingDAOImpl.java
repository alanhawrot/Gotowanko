package pl.gotowanko.android.db.dao;

import static pl.gotowanko.android.db.DateFormat.formatDateTime;
import static pl.gotowanko.android.db.TrainingSchema.ID;
import static pl.gotowanko.android.db.TrainingSchema.IMAGE;
import static pl.gotowanko.android.db.TrainingSchema.MODIFICATION_DATE;
import static pl.gotowanko.android.db.TrainingSchema.NAME;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.gotowanko.android.db.DateFormat;
import pl.gotowanko.android.db.TrainingCardsSchema;
import pl.gotowanko.android.db.TrainingSchema;
import pl.gotowanko.android.db.entity.Card;
import pl.gotowanko.android.db.entity.Training;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TrainingDAOImpl extends BaseDAO implements TrainingDAO {
	private CardDAO cardDAO;

	public TrainingDAOImpl(SQLiteDatabase db, CardDAO cardDAO) {
		super(db);
		this.cardDAO = cardDAO;
	}

	@Override
	public void insert(Training training) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(NAME, training.getName());
		contentValues.put(MODIFICATION_DATE, formatDateTime(training.getModificationDate()));
		contentValues.put(IMAGE, training.getImage());
		training.setId(db.insert(TrainingSchema.TABLE_NAME, null, contentValues));

		for (Card c : training.getCards(false)) {

			if (c.getId() == null)
				cardDAO.insert(c);
			else
				cardDAO.update(c);

			setRelation(TrainingCardsSchema.TABLE_NAME, TrainingCardsSchema.CARD_ID, c.getId(), TrainingCardsSchema.TRAINING_ID, training.getId());
		}
	}

	@Override
	public void update(Training training) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(NAME, training.getName());
		contentValues.put(MODIFICATION_DATE, formatDateTime(training.getModificationDate()));
		contentValues.put(IMAGE, training.getImage());
		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(training.getId()) };
		db.update(TrainingSchema.TABLE_NAME, contentValues, selection, selectionArgs);

		removeRelations(TrainingCardsSchema.TABLE_NAME, TrainingCardsSchema.TRAINING_ID, training.getId());

		for (Card c : training.getCards(false)) {

			if (c.getId() == null)
				cardDAO.insert(c);
			else
				cardDAO.update(c);

			setRelation(TrainingCardsSchema.TABLE_NAME, TrainingCardsSchema.CARD_ID, c.getId(), TrainingCardsSchema.TRAINING_ID, training.getId());
		}

	}

	@Override
	public void delete(Training training) {

		removeRelations(TrainingCardsSchema.TABLE_NAME, TrainingCardsSchema.TRAINING_ID, training.getId());
		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(training.getId()) };
		db.delete(TrainingSchema.TABLE_NAME, selection, selectionArgs);

		for (Card c : training.getCards()) {
			cardDAO.delete(c);
		}
	}

	@Override
	public List<Training> filter(String name) {
		List<Training> l = new ArrayList<Training>();
		StringBuilder b = new StringBuilder("SELECT id, name, modification_date, image_path ");
		b.append(String.format("FROM %s t ", TrainingSchema.TABLE_NAME));
		b.append(String.format("WHERE UPPER(%s) LIKE '%%%s%%'", NAME, name.toUpperCase(Locale.getDefault())));
		Cursor cursor = db.rawQuery(b.toString(), null);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Training t = new Training();
				t.setId(cursor.getLong(0));
				t.setName(cursor.getString(1));
				try {
					t.setModificationDate(DateFormat.formatDateTime(cursor.getString(2)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				t.setImage(cursor.getString(3));
				l.add(t);
			} while (cursor.moveToNext());
		}
		return l;

	}

	@Override
	public List<Training> getAll() {
		List<Training> l = new ArrayList<Training>();
		StringBuilder b = new StringBuilder("SELECT ");
		b.append(String.format("%s, %s, %s, %s ", ID, NAME, MODIFICATION_DATE, IMAGE));
		b.append(String.format("FROM %s ", TrainingSchema.TABLE_NAME));
		Cursor cursor = db.rawQuery(b.toString(), null);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Training t = new Training();
				t.setId(cursor.getLong(0));
				t.setName(cursor.getString(1));

				try {
					t.setModificationDate(DateFormat.formatDateTime(cursor.getString(2)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				t.setImage(cursor.getString(3));
				l.add(t);
			} while (cursor.moveToNext());
		}
		return l;
	}

	@Override
	public Training newTraining() {
		return new Training();
	}

	@Override
	public Training getById(long id) {
		StringBuilder b = new StringBuilder("SELECT id, name, modification_date, image_path ");
		b.append(String.format("FROM %s t ", TrainingSchema.TABLE_NAME));
		b.append(String.format("WHERE id = ?"));
		Cursor cursor = db.rawQuery(b.toString(), new String[] {
				String.valueOf(id)
		});
		if (cursor != null && cursor.moveToFirst()) {
			Training t = new Training();
			t.setId(cursor.getLong(0));
			t.setName(cursor.getString(1));
			try {
				t.setModificationDate(DateFormat.formatDateTime(cursor.getString(2)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			t.setImage(cursor.getString(3));
			return t;
		}
		return null;
	}

	@Override
	public Training get(String name) {
		StringBuilder b = new StringBuilder("SELECT id, name, modification_date, image_path ");
		b.append(String.format("FROM %s t ", TrainingSchema.TABLE_NAME));
		b.append(String.format("WHERE %s = ?", NAME));
		Cursor cursor = db.rawQuery(b.toString(), new String[] { name });
		if (cursor != null && cursor.moveToFirst()) {
			Training t = new Training();
			t.setId(cursor.getLong(0));
			t.setName(cursor.getString(1));
			try {
				t.setModificationDate(DateFormat.formatDateTime(cursor.getString(2)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			t.setImage(cursor.getString(3));
			return t;
		}
		return null;
	}

}
