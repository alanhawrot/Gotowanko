package pl.gotowanko.android.db;

import pl.gotowanko.android.base.GotowankoApplication;
import pl.gotowanko.android.db.dao.IngredientCategoryDAO;
import pl.gotowanko.android.db.dao.IngredientDAO;
import pl.gotowanko.android.support.IngredientsBuilder;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbSchema extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 34;
	public static final String DATABASE_NAME = "dblocal.db";
	private static final boolean AUTORESET = false;
	public boolean restarted = false;
	private GotowankoApplication application;

	public DbSchema(GotowankoApplication application) {
		super(application, DATABASE_NAME, null, DATABASE_VERSION);
		this.application = application;
	}

	public void onCreate(SQLiteDatabase db) {
		if (AUTORESET)
			reset(db);
		db.execSQL(TrainingSchema.createTable());
		db.execSQL(IngredientSchema.createTable());
		db.execSQL(IngredientCategorySchema.createTable());
		db.execSQL(CardSchema.createTable());
		String createTable = IngredientCategoryCategoriesSchema.createTable();
		db.execSQL(createTable);
		db.execSQL(IngredientCategoryIngredientsSchema.createTable());
		db.execSQL(CardIngredientsSchema.createTable());
		db.execSQL(IngredientAmmountSchema.createTable());
		db.execSQL(TrainingCardsSchema.createTable());
		db.execSQL(IngredientAmmountIngredientSchema.createTable());

	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		reset(db);
		onCreate(db);
		Context context = application.getApplicationContext();
		GotowankoApplication.setDb(db);
		IngredientCategoryDAO ingredientCategoryDAO = GotowankoApplication.getIngredientCategoryDAO();
		IngredientDAO ingredientDAO = GotowankoApplication.getIngredientDAO();
		new IngredientsBuilder(context, ingredientCategoryDAO, ingredientDAO)
				.build();

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);

	}

	protected void reset(SQLiteDatabase db) {
		restarted = true;
		db.execSQL(TrainingSchema.dropTable());
		db.execSQL(IngredientSchema.dropTable());
		db.execSQL(IngredientCategorySchema.dropTable());
		db.execSQL(CardSchema.dropTable());
		db.execSQL(IngredientCategoryCategoriesSchema.dropTable());
		db.execSQL(IngredientCategoryIngredientsSchema.dropTable());
		db.execSQL(CardIngredientsSchema.dropTable());
		db.execSQL(IngredientAmmountSchema.dropTable());
		db.execSQL(TrainingCardsSchema.dropTable());
		db.execSQL(IngredientAmmountIngredientSchema.dropTable());
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// onUpgrade(db, oldVersion, newVersion);
	}

}