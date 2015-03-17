package pl.gotowanko.android.listeners;

import pl.gotowanko.android.IngredientsActivity;
import android.content.DialogInterface;

public final class OnDontSaveIngredients implements android.content.DialogInterface.OnClickListener {
	/**
	 * 
	 */
	private final IngredientsActivity ingredientsActivity;

	/**
	 * @param ingredientsActivity
	 */
	public OnDontSaveIngredients(IngredientsActivity ingredientsActivity) {
		this.ingredientsActivity = ingredientsActivity;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		ingredientsActivity.finish();
	}
}