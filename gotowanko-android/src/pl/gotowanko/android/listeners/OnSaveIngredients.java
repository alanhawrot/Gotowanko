package pl.gotowanko.android.listeners;

import pl.gotowanko.android.IngredientsActivity;
import android.content.DialogInterface;

public final class OnSaveIngredients implements android.content.DialogInterface.OnClickListener {

	private final IngredientsActivity OnNotSaveIngredients;

	public OnSaveIngredients(IngredientsActivity ingredientsActivity) {
		OnNotSaveIngredients = ingredientsActivity;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		OnNotSaveIngredients.save();
		OnNotSaveIngredients.finish();

	}
}