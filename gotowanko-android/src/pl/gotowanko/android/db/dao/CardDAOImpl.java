package pl.gotowanko.android.db.dao;

import static pl.gotowanko.android.db.CardSchema.DESCRIPTION;
import static pl.gotowanko.android.db.CardSchema.DURATION;
import static pl.gotowanko.android.db.CardSchema.ID;
import static pl.gotowanko.android.db.CardSchema.TABLE_NAME;
import static pl.gotowanko.android.db.CardSchema.TIMER_DURATION;
import static pl.gotowanko.android.db.CardSchema.TITLE;
import static pl.gotowanko.android.db.DateFormat.formatTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import pl.gotowanko.android.db.CardIngredientsSchema;
import pl.gotowanko.android.db.CardSchema;
import pl.gotowanko.android.db.TrainingCardsSchema;
import pl.gotowanko.android.db.entity.Card;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.db.entity.Training;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CardDAOImpl extends BaseDAO implements CardDAO {
	IngredientAmmountDAO ingredientsAmmountDAO;

	public CardDAOImpl(SQLiteDatabase db, IngredientAmmountDAO ingredientsAmmountDAO) {
		super(db);
		this.ingredientsAmmountDAO = ingredientsAmmountDAO;
	}

	@Override
	public void insert(Card card) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DESCRIPTION, card.getDescription());
		if (card.getTimerDuration() != null)
			contentValues.put(DURATION, formatTime(card.getTimerDuration()));
		if (card.getExpectedTimeDuration() != null)
			contentValues.put(TIMER_DURATION, formatTime(card.getExpectedTimeDuration()));
		contentValues.put(TITLE, card.getTitle());
		card.setId(db.insert(TABLE_NAME, null, contentValues));

		for (IngredientAmmount ia : card.getIngredients()) {
			ingredientsAmmountDAO.insert(ia);
			putRelation(CardIngredientsSchema.TABLE_NAME, CardIngredientsSchema.INGREDIENT_ID, ia.getId(), CardIngredientsSchema.CARD_ID, card.getId());
		}
	}

	@Override
	public void update(Card card) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DESCRIPTION, card.getDescription());
		if (card.getTimerDuration() != null)
			contentValues.put(DURATION, formatTime(card.getTimerDuration()));
		if (card.getExpectedTimeDuration() != null)
			contentValues.put(TIMER_DURATION, formatTime(card.getExpectedTimeDuration()));
		contentValues.put(TITLE, card.getTitle());
		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(card.getId()) };
		db.update(TABLE_NAME, contentValues, selection, selectionArgs);
		removeRelations(CardIngredientsSchema.TABLE_NAME, CardIngredientsSchema.CARD_ID, card.getId());
		for (IngredientAmmount ia : card.getIngredients()) {
			if (ia.getId() != null)
				ingredientsAmmountDAO.update(ia);
			else
				ingredientsAmmountDAO.insert(ia);
			putRelation(CardIngredientsSchema.TABLE_NAME, CardIngredientsSchema.INGREDIENT_ID, ia.getId(), CardIngredientsSchema.CARD_ID, card.getId());
		}
	}

	@Override
	public void delete(Card card) {
		removeRelations(CardIngredientsSchema.TABLE_NAME, CardIngredientsSchema.CARD_ID, card.getId());
		for (IngredientAmmount ia : card.getIngredients()) {
			ingredientsAmmountDAO.delete(ia);
		}
		String selection = String.format("%s = ?", ID);
		String[] selectionArgs = { String.valueOf(card.getId()) };
		db.delete(TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public List<Card> filter(Training training) {
		List<Card> l = new ArrayList<Card>();
		StringBuilder b = new StringBuilder("SELECT ");
		b.append(String.format("%s, %s, %s, %s, %s ", ID, DESCRIPTION, DURATION, TIMER_DURATION, TITLE));
		b.append(String.format("FROM %s c ", CardSchema.TABLE_NAME));
		b.append(String.format("INNER JOIN %s ts ON ts.%s = c.%s ", TrainingCardsSchema.TABLE_NAME, TrainingCardsSchema.CARD_ID, ID));
		b.append(String.format("WHERE ts.%s = ?", TrainingCardsSchema.TRAINING_ID));
		String[] selectionArgs = { String.valueOf(training.getId()) };
		Cursor cursor = db.rawQuery(b.toString(), selectionArgs);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Card c = new Card();
				c.setId(cursor.getLong(0));
				c.setDescription(cursor.getString(1));
				String duration = cursor.getString(2);
				if (duration != null)
					try {
						c.setExpectedTimeDuration(formatTime(duration));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				String timerDuration = cursor.getString(3);
				if (timerDuration != null) {
					try {
						c.setTimerDuration(formatTime(timerDuration));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				c.setTitle(cursor.getString(4));
				l.add(c);
			} while (cursor.moveToNext());
		}
		return l;
	}

	public Card newCard() {
		return new Card();
	}
}
