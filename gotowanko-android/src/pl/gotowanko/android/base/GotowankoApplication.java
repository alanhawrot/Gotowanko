package pl.gotowanko.android.base;

import pl.gotowanko.android.db.DbSchema;
import pl.gotowanko.android.db.dao.CardDAO;
import pl.gotowanko.android.db.dao.CardDAOImpl;
import pl.gotowanko.android.db.dao.IngredientAmmountDAO;
import pl.gotowanko.android.db.dao.IngredientAmmountDAOImpl;
import pl.gotowanko.android.db.dao.IngredientCategoryDAO;
import pl.gotowanko.android.db.dao.IngredientCategoryDAOImpl;
import pl.gotowanko.android.db.dao.IngredientDAO;
import pl.gotowanko.android.db.dao.IngredientDAOImpl;
import pl.gotowanko.android.db.dao.TrainingDAO;
import pl.gotowanko.android.db.dao.TrainingDAOImpl;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class GotowankoApplication extends Application {
	private static DbSchema schema;
	private static CardDAO cardDAO;
	private static IngredientAmmountDAO ingredientAmmountDAO;
	private static IngredientCategoryDAO ingredientCategoryDAO;
	private static IngredientDAO ingredientDAO;
	private static TrainingDAO trainigDAO;
	private static SQLiteDatabase db;

	public GotowankoApplication() {
		super();
		schema = new DbSchema(this);
	}

	public static SQLiteDatabase getDb() {
		if (db == null)
			db = schema.getWritableDatabase();
		return db;
	}

	public static void setDb(SQLiteDatabase db) {
		GotowankoApplication.db = db;

	}

	public static CardDAO getCardDAO() {
		if (cardDAO == null)
			cardDAO = new CardDAOImpl(getDb(), getIngredientAmmountDAO());
		return cardDAO;
	}

	public static IngredientAmmountDAO getIngredientAmmountDAO() {
		if (ingredientAmmountDAO == null)
			ingredientAmmountDAO = new IngredientAmmountDAOImpl(getDb(), getIngredientDAO());
		return ingredientAmmountDAO;
	}

	public static IngredientCategoryDAO getIngredientCategoryDAO() {
		if (ingredientCategoryDAO == null) {
			ingredientCategoryDAO = new IngredientCategoryDAOImpl(getDb(), getIngredientDAO());
		}
		return ingredientCategoryDAO;
	}

	public static IngredientDAO getIngredientDAO() {
		if (ingredientDAO == null)
			ingredientDAO = new IngredientDAOImpl(getDb());
		return ingredientDAO;
	}

	public static TrainingDAO getTrainingDAO() {
		if (trainigDAO == null)
			trainigDAO = new TrainingDAOImpl(getDb(), getCardDAO());
		return trainigDAO;
	}

}
