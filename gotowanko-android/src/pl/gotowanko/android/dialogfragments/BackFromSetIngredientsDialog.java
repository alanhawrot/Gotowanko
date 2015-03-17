package pl.gotowanko.android.dialogfragments;

import pl.gotowanko.android.IngredientsActivity;
import pl.gotowanko.android.R;
import pl.gotowanko.android.listeners.OnDontSaveIngredients;
import pl.gotowanko.android.listeners.OnSaveIngredients;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class BackFromSetIngredientsDialog extends DialogFragment {

	public BackFromSetIngredientsDialog() {
		super();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		IngredientsActivity ingredientsActivity = (IngredientsActivity) getActivity();
		return new AlertDialog.Builder(ingredientsActivity)
				.setTitle(R.string.choosen_ingredients_were_not_saved)
				.setIcon(R.drawable.info)
				.setMessage(R.string.save_changes)
				.setPositiveButton(R.string.ok, new OnSaveIngredients(ingredientsActivity))
				.setNegativeButton(R.string.no, new OnDontSaveIngredients(ingredientsActivity))
				.setNeutralButton(R.string.cancel, null)
				.setCancelable(false)
				.create();
	}
}